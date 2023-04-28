package cn.ttitcn.framework.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ttitcn.system.entity.SysDictData;
import cn.ttitcn.system.service.SysDictDataService;

/**
 * 页面调用的字典service
 */
@Service("dict")
public class DictService {
	@Autowired
	private SysDictDataService dictDataService;

	/**
	 * 根据字典类型查询字典数据信息
	 * 
	 * @param dictType 字典类型
	 * @return 参数键值
	 */
	public List<SysDictData> getType(String dictType) {
		SysDictData data = new SysDictData();
		data.setDictType(dictType);
		return dictDataService.listByType(data);
	}

	/**
	 * 根据字典类型和字典键值查询字典数据信息
	 * 
	 * @param dictType  字典类型
	 * @param dictValue 字典键值
	 * @return 字典标签
	 */
	public String getLabel(String dictType, String dictValue) {
		return dictDataService.getDictLabel(dictType, dictValue);
	}
}
