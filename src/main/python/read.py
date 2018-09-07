from collections import defaultdict

import pymysql
import os
import json
from copy import deepcopy
mysql_host = os.getenv("HOST","localhost")
mysql_port = int(os.getenv("PORT","3306"))
mysql_user = os.getenv("USER","root")
mysql_pass = os.getenv("PASS","")
mysql_db = os.getenv("DB","fever")

def clean(s):
    if b"SUPPORTED" in s:
        return "SUPPORTS"
    if b"REFUTED" in s:
        return "REFUTES"
    if b"NOT_ENOUGH_INFO" in s:
        return "NOT ENOUGH INFO"


conn = pymysql.connect(host=mysql_host, port=mysql_port, user=mysql_user, passwd=mysql_pass, db=mysql_db)

cur = conn.cursor()

cur.execute("select claim.originalId, annotation.id, annotation.label, ce_1.page, ce_1.line, ce_2.page, ce_2.line  "
            "from claim "
            "JOIN task on task.claimId = claim.id "
            "JOIN annotation_assignment ON annotation_assignment.taskId = task.id "
            "JOIN annotation on annotation.assignmentId = annotation_assignment.id "
            "left join individual_evidence on individual_evidence.annotationId = annotation.id "
            "left join candidate_evidence as ce_1 on ce_1.id = individual_evidence.evidenceId "
            "left join partial_evidence on partial_evidence.annotationId = annotation.id "
            "left join candidate_evidence as ce_2 on ce_2.id = partial_evidence.evidenceId")

claims = defaultdict(lambda: defaultdict(lambda :
                                         {"label": None, "evidence": [], "p_evidence":[]} ))

for row in cur:
    id = row[0]
    annotation_id = row[1]

    e1 = row[3]
    e2 = row[4]
    p1 = row[5]
    p2 = row[6]

    claims[id][annotation_id]["label"] = clean(row[2])

    if e1 is not None:
        claims[id][annotation_id]["evidence"].append((e1, e2))

    if p1 is not None:
        claims[id][annotation_id]["p_evidence"].append((p1, p2))

cur.close()
conn.close()


def check_consistency(claims,indata,add=False,ignore=list(),evidence_only=list()):
    chcount = defaultdict(int)
    lines = []
    for line in indata:
        suggested_label = line["label"]
        if line["id"] in claims:
            annotated = claims[line["id"]]

            for idx,(k,item) in enumerate(annotated.items()):

                if item["label"] != line["label"] \
                        and item["label"] != "NOT ENOUGH INFO"\
                        and line["label"] != "NOT ENOUGH INFO":
                    if not add:
                        print(k)
                        print(item)
                        print(line)
                        print()

                    if k in ignore:
                        continue

                    chcount[line["label"]] += 1

                    if add:
                        for e in item["evidence"]:
                            line["evidence"].append([[line['id'], k, e[0], e[1]]])
                        pe = []
                        for p in item["p_evidence"]:
                            pe.append([line['id'], k, p[0], p[1]])
                        line["evidence"].append(pe)

                        for evidence in line["evidence"]:
                            if any([e[2] is None or e[3] is None for e in evidence]):
                                line["evidence"].remove(evidence)

                        line["evidence"] = [e for e in line["evidence"] if e]

                        if k not in evidence_only:
                            suggested_label = item["label"]
        line["label"] = suggested_label
        lines.append(line)
    print(chcount)
    return lines

def check_new_evidence_nei(claims, indata, add=False):
    chcount = defaultdict(int)
    lines = []

    for line in indata:

        suggested_label = line["label"]
        if line["id"] in claims:
            annotated = claims[line["id"]]
            for idx, (k, item) in enumerate(annotated.items()):

                if item["label"] != line["label"] \
                        and item["label"] != "NOT ENOUGH INFO"\
                        and line["label"] == "NOT ENOUGH INFO":
                    if not add:
                        print(k)
                        print(item)
                        print(line)
                        print()
                    chcount[item["label"]] += 1

                    if add:
                        for e in item["evidence"]:
                            line["evidence"].append([[line['id'],k,e[0],e[1]]])
                        pe = []
                        for p in item["p_evidence"]:
                            pe.append([line['id'],k,p[0],p[1]])
                        line["evidence"].append(pe)

                        for evidence in line["evidence"]:
                            if any([e[2] is None or e[3] is None for e in evidence]):
                                line["evidence"].remove(evidence)

                        line["evidence"] = [e for e in line["evidence"] if e]
                        suggested_label = item["label"]

            line["label"] = suggested_label

        #del line["all_evidence"]
        lines.append(line)
    print(chcount)
    return lines



def check_new_evidence_sr(claims,indata,add=False):
    chcount = defaultdict(int)
    lines = []

    for line in indata:
        if line["id"] in claims:
            annotated = claims[line["id"]]
            for idx, (k, item) in enumerate(annotated.items()):
                if item["label"] == line["label"] and \
                        item["label"] != "NOT ENOUGH INFO" \
                        and line["label"] != "NOT ENOUGH INFO":
                    if not add:
                        print(k)
                        print(item)
                        print(line)
                        print()

                    for e_page, e_line in item["evidence"]:
                        evidence_exists = any(
                            [[e_page, e_line] in [[e[2], e[3]] for e in egroup] for egroup in line["evidence"]])
                        if not evidence_exists:
                            line["evidence"].append([[line['id'], k, e_page, e_line]])
                            chcount["new_complete"] += 1

                    if len(item["p_evidence"]):
                        partial_evidence_exists = all([ any(
                                [[p_page, p_line] in [[e[2], e[3]] for e in egroup] for egroup in line["evidence"]])
                                for p_page,
                                    p_line in item["p_evidence"]])

                        if not partial_evidence_exists:
                            chcount["new_partial"] += 1
                            line["evidence"].append([[line['id'], k, p_page, p_line] for p_page,p_line in item["p_evidence"]])


        lines.append(line)
    print(chcount)
    return lines

with open("../../Downloads/blind_private.jsonl") as f:
    indata = []
    for line in f:
        indata.append(json.loads(line))
    id2 = deepcopy(indata)
    id1 = check_consistency(claims, indata, True,ignore=[615,33,678,750,845,677,455,435],evidence_only=[133,829])
    assert id1!=id2


    indata = check_new_evidence_nei(claims, indata, True)
    indata = check_new_evidence_sr(claims,indata,True)


with open("../../Downloads/blind_private_extended.jsonl","w+") as f:
    for item in indata:
        f.write(json.dumps(item)+"\n")