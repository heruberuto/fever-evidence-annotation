package uk.ac.sheffield.nlp.fever.annotation.dao;

import uk.ac.sheffield.nlp.fever.annotation.model.Page;

public interface PageDAO extends GenericDAO  {

    Page get(long pageId);
    Page get(String name);
    String getLine(String page, int line);

}
