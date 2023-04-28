package cn.ttitcn.system.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ttitcn.common.constant.UserConstants;
import cn.ttitcn.common.core.base.BaseServiceImpl;
import cn.ttitcn.common.util.StringUtils;
import cn.ttitcn.system.dao.SysDictDataDao;
import cn.ttitcn.system.dao.SysDictTypeDao;
import cn.ttitcn.system.entity.SysDictType;
import cn.ttitcn.system.service.SysDictTypeService;

@Service
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictType> implements SysDictTypeService {

    @Autowired
    private SysDictTypeDao dictTypeDao;
    @Autowired
    private SysDictDataDao dictDataDao;

    @Override
    public String checkDictTypeUnique(SysDictType dict) {
        Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
        SysDictType dictType = dictTypeDao.checkDictTypeUnique(dict);
        if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue()) {
            return UserConstants.DICT_TYPE_NOT_UNIQUE;
        }
        return UserConstants.DICT_TYPE_UNIQUE;
    }

    @Override
    @Transactional
    public int update(SysDictType dictType) {
        SysDictType oldDict = dictTypeDao.getById(dictType.getDictId());
        dictDataDao.updateDictDataType(oldDict.getDictType(),dictType.getDictType());
        return dictTypeDao.update(dictType);
    }

}
