package org.yuantai.system.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单缓存实体类
 * @author zhangle
 */
@Entity
@Table(name = "sys_menu_cache")
public class MenuCache extends Pojo {

	public static final String TYPE_ROLE = "role"; // 类型:角色与菜单的缓存
	public static final String TYPE_ORGAN = "organ"; // 类型:组织机构与菜单的缓存

	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", length = 36)
	private String id;

	@Column(name = "menu_id", length = 36)
	private String menuId;

	@Column(name = "data_id", length = 36)
	private String dataId;

	@Column(name = "type", length = 16)
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}