package com.common.util;

public class Web {
//	public static String URL="http://10.0.2.2:8080/TeachingAffairs/app/";
	public static String URL="http://10.0.2.2:8080//BlueBerryServer/app/";
	/**
	 * 图片地址
	 */
	public final static String imgUrl="http://10.0.2.2:8080//BlueBerryServer/files/";
	/**登录帐号*/
	public final static String userLogin=URL+"user-userLogin";
	public final static String getAllIn=URL+"inform-getAllIn";
	public final static String getGameXD=URL+"basemark-getGameXD";
	public final static String getGamePC=URL+"basemark-getGamePC";
	public final static String checkUserLogin=URL+"user-checkUserLogin";
	public final static String saveUser=URL+"user-saveUser";
	public final static String addBasemark=URL+"basemark-addBasemark";
	/**获得所有的图片*/
	public final static String getAllPictures=URL+"picture-getAllPictures";
	/*上传图片*/
	public static final String addPicture = URL+"picture-addPicture";
	/**添加评论*/
	public static final String addComment=URL+"comment-addComment";
	/**get图片评论*/
	public static final String getCommentsForPictures=URL+"comment-getCommentsForPictures";
	/**get论坛评论*/
	public static final String getCommentsForExchange=URL+"comment-getCommentsForExchange";
	/**获得论坛信息*/
	public final static String getExchanges=URL+"exchange-getExchanges";
	/**添加论坛信息*/
	public final static String addExchange=URL+"exchange-addExchange";
	/**添加找伙伴信息*/
	public final static String addFriend=URL+"foundingfriends-addFriend";
	/**新增*/
	public final static String getFriends=URL+"foundingfriends-getFriends";
}
