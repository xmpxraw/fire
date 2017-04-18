package org.yuantai.basic.service;

import java.util.Map;

import org.yuantai.basic.pojo.Changelog;
import org.yuantai.system.service.BaseService;

public interface ChangelogService extends BaseService<Changelog> {

	public Map<String,Changelog> findForAboutus();
}
