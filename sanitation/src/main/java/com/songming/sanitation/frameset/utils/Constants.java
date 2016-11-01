package com.songming.sanitation.frameset.utils;


public class Constants {

	/** 正式服务器IP */
	public final static String SERVER_URL = "http://117.78.60.138:8080/wss";
	public final static String SERVER_IP = "http://117.78.60.138:8080/";
	public final static String IMAGE_URL = "http://117.78.60.138:8081/wsFile";
	
	/** apk下载路径 */
	public final static String SERVER_APK = "http://mca.songmingkeji.com:8080/medicalcms/";
	/** 检查版本更新 */
	public final static String SERVER_URL_SM ="http://mca.songmingkeji.com:8090/medical/";
	public static final String FINDLATEVERSION = "/version/findLateVersion.action";

	/** 内网测试IP */
//	public final static String SERVER_URL = "http://192.168.0.252:8887/wss";
//	public final static String SERVER_IP = "http://192.168.0.252:8887/";
//	public final static String IMAGE_URL = "http://192.168.0.252:8000/wsFile";
	/** 登录 */
	public final static String LOGIN = "/sys/user/login";

	/** 修改密码 */
	public final static String MODIFYPSW = "/sys/user/password";

	/** 通讯录 */
	public final static String CONTACTS = "/sys/staff/contacts";

	/** 指派人员列表 */
	public final static String TEAMLIST = "/sys/staff/assign/contacts";
	
	/** 发送公告人员*/
	public final static String STAFF_COMMENT="/sys/staff/comment/contacts"; 
	
	/** 发布公告接口*/
	public final static String SEND_MASSAGE="/sys/message/send";
	
	/** 事件指派 */
	public final static String ASSIGN = "/event/assign";

	/** 事件上报 */
	public final static String REPORTEVENT = "/event/add";

	/** 文件上传 */
	public final static String UPLOADFILE = "/file/upload";

	/** 机构部门列表 */
	public final static String ORGLIST = "/sys/org/list";

	/** 签到情况查询列表 */
	public final static String SIGNLIST = "/sys/staff/org/sign";

	/** 车辆关联记录 */
	public final static String CAR_USED_RECORDS = "/staff/ref/list";

	/** 车辆列表 */
	public final static String CARLIST = "/car/ref/list";
	
	
	/** 车辆保养列表*/
	public final static String CAR_MAINTAIN_LIST="/car/maintain/list";
	
	/** 司机车辆关联 */
	public final static String CAR_BIND = "/car/ref/driver";

	/** 取消车辆关联 */
	public final static String CAR_UNBIND = "/car/ref/cancel";

	/** 巡查情况列表 */
	public final static String JOBCHECKLIST = "/timesheet/patrol/staff/list";

	/** 巡查情况详情 */
	public final static String JOBCHECKDETAIL = "/timesheet/patrol/get";

	/** 人员轨迹上传 */
	public final static String STAFFLOCATION = "/timesheet/stafftrack/upload";
	
	/** 车辆坐标上传 */
	public final static String CARLOCATION = "/timesheet/cartrack/upload";

	/** 车辆轨迹列表 */
	public final static String LOCATIONLIST = "/timesheet/cartrack/list";

	/** 根据线路查询线路详情 */
	public final static String FINDLINEDETAILS = "/distributionGrace/findLineDetails.action";

	/** 签到 */
	public final static String SIGN = "/timesheet/jobsign/upload";

	/** 文件上传 */
	public final static String WORKUPLOAD = "/file/upload";

	/** 事件列表 */
	public final static String WORKLIST = "/event/list";
	/** 事件列表详情 */
	public final static String WORKLISTDETAIL = "/event/get";

	/** 系统消息提示 */
	public final static String SYSTEMMESSAGE = "/sys/message/list";

	/** 车辆保养界面下拉选择 */
	public final static String CARTYPE = "/sys/dict/list";
	/** 车辆类别 */
	public final static String CARID = "/car/ref/list";
	/** 车辆保养 */
	public final static String CARDATA = "/car/maintain/add";
	/** 事件总数 */
	public final static String WORKLISTCOUNT = "/event/count";
	
	/** 相片上传 */
	public final static String PHOTOUPLOAD = "/timesheet/patrol/add";

	/** 事件总数 */
	public final static String WORKCOMPLETE = "/event/complete";

	/** 员工签到 */
	public final static String STAFFSIGN = "/sys/staff/org/sign";
	
	/** 员工签到修改*/
	public final static String STAFFSIGNALTER="/sys/staff/sign/list";
	/** 机构下员工列表 */
	public final static String ORGANIZATION_DATA = "/sys/org/staff";
	
	/** 巡查情况列表 */
	public final static String WORKCHECKLIST = "/timesheet/patrol/list";
	
	/** 签到提醒 */
	public static final String NOTIFY_SIGN = "/timesheet/jobsign/remind";
	
	/** 查询员工最新位置 */
	public static final String FINDLATESTPOSITION = "/timesheet/stafftrack/newesttude";
	
	/** 工单处理未读展示*/
	public static final String UNREAD = "/event/unread";
	
	/** 消息事件阅读 */
	public static final String MESSAGE_READ = "/event/read";
	
	/** 消息事件阅读 */
	public static final String TDAYJOB_BOSSLIST = "/dayjob/super/list";
	
	/** 发起审批 */
	public static final String APPROVAL_APPLY = "/jobaudit/submit";
	/** 审批结果列表 */
	public static final String APPROVAL_RESULT_LIST = "/jobaudit/result";
	/** 审批 */
	public static final String APPROVAL = "/jobaudit/audit";
	/** 我审批的 */
	public static final String APPROVAL_MINE = "/jobaudit/list";
	
	/** 我的考勤列表*/
	public static final String MYSIGN_LIST="/timesheet/jobsign/mysign";
	/** 修改用户头像 */
	public static final String UPDATEHEAD = "/sys/user/photo";
	/** 查询用户信息 */
	public static final String GETUSERINFO = "/sys/user/get";
	
	/** 用户头像接口*/
	public static final String USET_PHOTO="/sys/user/photo";
	
	/** 安排工作*/
	public static final String DAYJOB_ASSIGN="/dayjob/assign";
	
	/** 收到下级汇报*/
	public static final String LOWER_LIST="/dayjob/lower/list";
	
	/** 汇报上级人员*/
	public static final String JOB_CONTACTS="/sys/staff/job/contacts";
	
	/** 汇报上级内容*/
	public static final String DARJOB_REPORT="/dayjob/report";
	
	
	/*
	 * 确认网点
	 */
	public final static String MODIFYSTATIONSTATUS = "/appStation/modifyStationStatus.action";

	/*
	 * 获取用户金纬度
	 */
	public final static String FINDLONGTITUDEAANLATITUDE = "/distributionGrace/findLongtitudeAanLatitude.action";

	/*
	 * 提交经纬度
	 */
	public final static String ADDDISTRIBUTIONGRACE = "/distributionGrace/addDistributionGrace.action";

	/*
	 * 今日任务
	 */
	public final static String FINDROUTEPAGING = "/distributionGrace/findRoutePaging.action";

	/*
	 * 分页查询用户通知
	 */
	public final static String FINDPAGINGMESSAGEPUSHAPP = "/messagePushApp/findPagingMessagePushApp.action";

	/*
	 * 根据id查询通知详情
	 */
	public final static String FINDBYIDMESSAGEPUSHAPP = "/messagePushApp/findByIdMessagePushApp.action";

	/*
	 * 根据id查询用户为查看的信息数目
	 */
	public final static String FINDNEWESTCOUNT = "/messagePushApp/findNewestCount.action";

	/** 天气预报API */
	public static final String WEATHERAPI = "http://apis.baidu.com/apistore/weatherservice/cityname";

	/** 百度推送APIKEY */
	public static final String PUSH_LOGIN_TYPE_API_KEY = "3jviNaEwIcGplO3CRE5GuC85";

	/**
	 * 定位标记
	 */
	public final static String SIGNFLAG = "signflag";

	
}
