import argparse
import json
import sys
from fever.scorer import fever_score
from prettytable import PrettyTable
from copy import deepcopy
parser = argparse.ArgumentParser()


parser.add_argument("--prediction",type=str)
parser.add_argument("--num_subs",type=int)
parser.add_argument("--actual_rescore",type=str)
parser.add_argument("--actual_original",type=str)

args = parser.parse_args()



actual_original = []
actual_rescore = []

with open(args.actual_rescore, "r") as actual_file:
    for line in actual_file:
        actual_rescore.append(json.loads(line))

with open(args.actual_original, "r") as actual_file:
    for line in actual_file:
        actual_original.append(json.loads(line))



deltatab = PrettyTable()
deltatab.field_names = ["∆ FEVER Score", "∆ Label Accuracy", "∆ Evidence Precision", "∆ Evidence Recall", " ∆ Evidence F1"]

for i in range(1,args.num_subs+1):

    predictions = []

    with open(args.prediction + " " + str(i),"r") as predictions_file:
        for line in predictions_file:
            predictions.append(json.loads(line))

    print("Team {0}".format(i))
    p1 = deepcopy(predictions)
    p2 = deepcopy(predictions)

    oscore,oacc,oprecision,orecall,of1 = fever_score(p1,actual_original)
    nscore,nacc,nprecision,nrecall,nf1 = fever_score(p2,actual_rescore)
    dscore,dacc,dprecision,drecall,df1 = nscore-oscore,nacc-oacc,nprecision-oprecision,nrecall-orecall,nf1-of1

    tab = PrettyTable()
    tab.field_names = ["","FEVER Score", "Label Accuracy", "Evidence Precision", "Evidence Recall", "Evidence F1"]
    tab.add_row(("Original",round(oscore,4),round(oacc,4),round(oprecision,4),round(orecall,4),round(of1,4)))
    tab.add_row(("Rescore",round(nscore,4),round(nacc,4),round(nprecision,4),round(nrecall,4),round(nf1,4)))
    tab.add_row(("∆",round(dscore,4),round(dacc,4),round(dprecision,4),round(drecall,4),round(df1,4)))
    deltatab.add_row((round(dscore, 4), round(dacc, 4), round(dprecision, 4), round(drecall, 4), round(df1, 4)))

    print(tab)
    print("")
    print("")
    print("")

print(deltatab)

for row in deltatab._rows:
    print(", ".join([str(a) for a in row]))