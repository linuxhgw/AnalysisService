package edu.jhun.service;

import edu.jhun.dao.AttributeDAO;
import edu.jhun.dao.MemberDAO;
import edu.jhun.dao.SchemeDAO;

/**
 * Created by hgw on 2018/7/5.
 */
public class SchemeListService {
    // 可优化
    public String LoadFormulaList1() {
        String rs = new SchemeDAO().SchemeResult();
        return rs;
    }

    public String LoadFormula(String SchemeId) {
        String rs = null;
        rs = new MemberDAO().MemberResult(SchemeId);
        return rs;

    }

    public String GetAttributeList(String mumberID) {
        String rs = null;
        rs = new AttributeDAO().AttributeResult(mumberID);
        return rs;
    }
}
