package org.yuantai.system.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.yuantai.common.util.StringUtil;
import org.yuantai.system.dao.OrganDataDao;
import org.yuantai.system.pojo.OrganData;

@Repository
public class OrganDataDaoImpl extends BaseDaoImpl<OrganData> implements OrganDataDao{

	@Override
	public void add(OrganData obj) {
		obj.setSeqNum(getNextSeqNum());
		obj.setCreateTime(new Date());
		super.add(obj);
	}
	
	public List<OrganData> getDataByOrganId(String organId){
		Criteria criteria=getCriteria();
		criteria.add(Restrictions.eq("organId", organId));
		criteria.addOrder(Order.asc("seqNum"));
		return criteria.list();
	}
	
	public void add(String organId,OrganData[] organDatas) {
		
		if(organDatas==null) return;
		for(OrganData data:organDatas) {
			if(StringUtil.isEmpty(data.getId())) {
				data.setOrganId(organId);
				add(data);
			} else{
				update(data);
			}
		}
		
		//清除无效的脏数据
		if(organDatas.length==0) {
			executeUpdate("delete OrganData where organId=?", organId);
		} else {
			Query query=createQuery("delete OrganData where organId=:organId and id not in(:dataId)");
			query.setString("organId", organId);
			query.setParameterList("dataId", toIdArray(organDatas));
			query.executeUpdate();
		}
	}
	
	private String[] toIdArray(OrganData[] datas) {
		String[] id=new String[datas.length];
		for(int i=0;i<datas.length;i++) {
			id[i]=datas[i].getId();
		}
		return id;
	}

	@Override
	public List<OrganData> getOrganByCode(String code, String value) {
		Criteria criteria=getCriteria();
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("value", value));
		criteria.addOrder(Order.asc("seqNum"));
		return criteria.list();
	}
}
