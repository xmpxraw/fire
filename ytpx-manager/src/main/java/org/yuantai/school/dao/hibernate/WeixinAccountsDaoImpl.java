package org.yuantai.school.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.yuantai.school.dao.WeixinAccountsDao;
import org.yuantai.school.pojo.WeixinAccounts;
import org.yuantai.system.dao.hibernate.BaseDaoImpl;

@Repository
public class WeixinAccountsDaoImpl extends BaseDaoImpl<WeixinAccounts> implements WeixinAccountsDao {

	@Override
	public List<WeixinAccounts> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(WeixinAccounts.class);
    	
		return super.find(criteria);
	}
	@Override
	public List<WeixinAccounts> checkBySysCode(String sysCode) {
		
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("school.checkWeixinAccountsBySysCode");
			query.setString("sysCode", sysCode);
		} 

		return query.list();
	}
	
}
