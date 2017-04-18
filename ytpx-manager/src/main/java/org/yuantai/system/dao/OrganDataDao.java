package org.yuantai.system.dao;

import java.util.List;

import org.yuantai.system.pojo.OrganData;

public interface OrganDataDao extends BaseDao<OrganData>{
	
	/**
	 * 根据组织机构编号获取组织机构数据
	 * @return
	 */
	public List<OrganData> getDataByOrganId(String organId);
	
	/**
	 * 批量更新组织机构数据
	 * @return
	 */
	public void add(String organId,OrganData[] organDatas);

	/**
	 * 根据codet和value获取绑定数据
	 * @param code
	 * @param value
	 * @return
	 */
	public List<OrganData> getOrganByCode(String code,String value);
}
