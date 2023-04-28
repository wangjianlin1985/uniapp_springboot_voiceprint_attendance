package cn.ttitcn.web.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.RandomUtil;
import cn.ttitcn.common.core.base.BaseController;
import cn.ttitcn.common.core.domain.AjaxResult;
import cn.ttitcn.common.core.page.TableDataInfo;
import cn.ttitcn.common.core.text.Convert;
import cn.ttitcn.common.util.AESUtil;
import cn.ttitcn.common.util.DateUtils;
import cn.ttitcn.framework.shiro.service.SysPasswordService;
import cn.ttitcn.framework.util.ShiroUtils;
import cn.ttitcn.system.dao.SysUserRoleDao;
import cn.ttitcn.system.entity.SysAtt;
import cn.ttitcn.system.entity.SysUser;
import cn.ttitcn.system.entity.SysUserRole;
import cn.ttitcn.system.service.SysAttService;
import cn.ttitcn.system.service.SysUserService;
import cn.ttitcn.util.CreateFeature;
import cn.ttitcn.util.SearchFeature;
import cn.ttitcn.util.XFYunConstant;

/**
 * 系统相关接口
 */
@RestController
@RequestMapping("openapi/system")
@CrossOrigin
public class SysApiController extends BaseController{

	@Autowired
	private SysUserService userService;
	@Autowired
	private SysPasswordService passwordService;
	@Autowired
	private SysUserRoleDao userRoleDao;
	@Autowired
	private SysAttService attService;
	
	// 登录（用户名密码登录）
	@RequestMapping("loginNocode")
	public AjaxResult loginNocode(@RequestBody Map<String, Object> map) {
		String loginName = Convert.toStr(map.get("username"));
		String password = Convert.toStr(map.get("password"));
		SysUser user = new SysUser();
		user.setLoginName(loginName);
		SysUser result = userService.selectUserByLoginName(user);
		if (result == null) {
			return error("用户名不存在");
		}
		String encrPwd = passwordService.encryptPassword(result.getLoginName(), password, result.getSalt());
		if (!encrPwd.equals(result.getPassword())) {
			return error("密码错误");
		}
		return AjaxResult.success(result);
	}
	
	// 登录(声纹登录)
	@RequestMapping("loginVoice")
	public AjaxResult loginVoice(@RequestBody Map<String, Object> map) {
		String path = Convert.toStr(map.get("path"));
		JSONObject object = SearchFeature.doSearchFeature(XFYunConstant.REQUESTURL,XFYunConstant.APPID,XFYunConstant.APISECRET,XFYunConstant.APIKEY,path );
		int r = 0;
		try {
			JSONArray array = object.getJSONArray("scoreList");
			if (array.size() > 0) {
				JSONObject o = array.getJSONObject(0);
				float score = o.getFloatValue("score");
				if (score >= XFYunConstant.SCORE) {
					String featureId = o.getString("featureId");
					SysUser user = userService.getByFeatureId(featureId);
					if (user == null) {
						return error("声纹未匹配，登录失败");
					} else {
						return AjaxResult.success(user);
					}
				} else {
					return error("声纹未匹配，登录失败");
				}
			}
		} catch (Exception e) {
			return error("声纹未匹配，登录失败");
		}
		return toAjax(r);
	}
	
	
	// 考勤（手动）
	@RequestMapping("att")
	public AjaxResult attVoice(@RequestBody SysAtt att) {
		att.setTime(DateUtils.getTime());
		att.setStatus("1");
		return toAjax(attService.update(att));
	}
	
	// 考勤（声纹）
	@RequestMapping("attVoice")
	public AjaxResult attVoice(@RequestBody Map<String, Object> map) {
		String date = DateUtils.getDate();
		String path = Convert.toStr(map.get("path"));
		JSONObject object = SearchFeature.doSearchFeature(XFYunConstant.REQUESTURL,XFYunConstant.APPID,XFYunConstant.APISECRET,XFYunConstant.APIKEY,path );
		int r = 0;
		try {
			JSONArray array = object.getJSONArray("scoreList");
			if (array.size() > 0) {
				JSONObject o = array.getJSONObject(0);
				float score = o.getFloatValue("score");
				if (score >= XFYunConstant.SCORE) {
					String featureId = o.getString("featureId");
					SysUser user = userService.getByFeatureId(featureId);
					SysAtt searchAtt = new SysAtt();
					searchAtt.setUser(user);
					searchAtt.setDate(date);
					SysAtt att = attService.getByUserAndDate(searchAtt);
					if (att == null) {
						searchAtt.setTime(DateUtils.getTime());
						searchAtt.setStatus("1");
						r += attService.add(searchAtt);
					} else {
						att.setStatus("1");
						att.setTime(DateUtils.getTime());
						r += attService.update(att);
					}
				} else {
					return error("声纹未匹配，考勤失败");
				}
			}
		} catch (Exception e) {
			return error("声纹未匹配，考勤失败");
		}
		return toAjax(r);
	}
	
	// 查询我的历史考勤记录
	@RequestMapping("attList")
	public TableDataInfo attList(@RequestBody SysAtt att) {
		if (att == null) {
			att = new SysAtt();
		}
		SysUser user = new SysUser();
		user.setUserId(getHeaderUserId());
		att.setUser(user);
		List<SysAtt> list = attService.list(att);
		return getDataTable(list);
	}
	
	
	// 获取我的今日考勤
	@RequestMapping("getAttToday")
	public AjaxResult getAttToday() {
		Long userId = getHeaderUserId();
		String date = DateUtils.getDate();
		SysUser user = new SysUser();
		user.setUserId(userId);
		SysAtt att = new SysAtt();
		att.setUser(user);
		att.setDate(date);
		SysAtt newAtt = attService.getByUserAndDate(att);
		if (newAtt != null) {
			return AjaxResult.success(newAtt);
		} else {
			att.setStatus("0");
			att.setTime("");
			attService.add(att);
			return AjaxResult.success(att);
		}
	}
	
	
	// 声纹注册（注册到讯飞）
	@RequestMapping("registerVoice")
	public AjaxResult registerVoice(@RequestBody Map<String, Object> map) {
		String path = Convert.toStr(map.get("path"));
		String featherId = RandomUtil.randomString(18);
		JSONObject object = CreateFeature.doCreateFeature(XFYunConstant.REQUESTURL,XFYunConstant.APPID,XFYunConstant.APISECRET,XFYunConstant.APIKEY,path ,featherId);
		System.out.println("object=====================" + object);
		if (object == null) {
			return error("录音识别失败，请重新录入");
		}
		return AjaxResult.success(object);
	}
	
	// 更新声纹（暂未使用）
	@RequestMapping("updateVoice")
	public AjaxResult updateVoice(@RequestBody Map<String, Object> map) {
		String path = Convert.toStr(map.get("path"));
		String featherId = Convert.toStr(map.get("featherId"));
		JSONObject object = CreateFeature.doCreateFeature(XFYunConstant.REQUESTURL,XFYunConstant.APPID,XFYunConstant.APISECRET,XFYunConstant.APIKEY,path,featherId);
		return AjaxResult.success(object);
	}
	
	// 用户注册
	@RequestMapping("register")
	public AjaxResult register(@RequestBody SysUser user) {
		user.setSalt(ShiroUtils.randomSalt());
		user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
		user.setOldpwd(AESUtil.encrypt(user.getPassword()));
		user.setRoleId(2L);
		user.setDeptId(103L);
		user.setUserType(0);
		user.setStatus("0");
		int r = userService.add(user);
		
		SysUserRole ur = new SysUserRole();
		ur.setUserId(user.getUserId());
		ur.setRoleId(user.getRoleId());
		r += userRoleDao.add(ur);
		return toAjax(r);
	}
	
	// 个人信息查询
	@RequestMapping("userinfo")
	public AjaxResult userinfo() {
		SysUser user = userService.getById(getHeaderUserId());
		return AjaxResult.success(user);
	}
	
	// 更新手机号
	@RequestMapping("updatePhonenumber")
	public AjaxResult updatePhonenumber(@RequestBody SysUser user) {
		user.setUserId(getHeaderUserId());
		return toAjax(userService.updatePhonenumber(user));
	}
	
	
	/**
	 * 修改重置密码
	 * @param map
	 * @return
	 */
	@RequestMapping("updatePwd")
	public AjaxResult updatePwd(@RequestBody Map<String, Object> map) {
		String username = Convert.toStr(map.get("username"));
		String password = Convert.toStr(map.get("password"));
		SysUser searchUser = new SysUser();
		searchUser.setLoginName(username);
		SysUser user = userService.selectUserByLoginName(searchUser);
		if (user == null) {
			return error("用户名错误");
		}
		
		user.setSalt(ShiroUtils.randomSalt());
		user.setOldpwd(AESUtil.encrypt(password));
		user.setPassword(passwordService.encryptPassword(user.getLoginName(), password, user.getSalt()));
		return toAjax(userService.resetUserPwd(user));
	}
	
	
	// 更新姓名
	@RequestMapping("updateUserName")
	public AjaxResult updateUserName(@RequestBody SysUser user) {
		user.setUserId(getHeaderUserId());
		return toAjax(userService.updateUserName(user));
	}
	
	
	// 检查用户名是否重复
	@RequestMapping("checkLoginName/{loginName}")
	public AjaxResult checkLoginName(@PathVariable String loginName) {
		SysUser user = new SysUser();
		user.setLoginName(loginName);
		String result = userService.checkLoginNameUnique(user);
		Map<String, Object> map = new HashMap<>();
		map.put("result", result); // 1重复 0不重复
		return AjaxResult.success(map);
	}
	
}
