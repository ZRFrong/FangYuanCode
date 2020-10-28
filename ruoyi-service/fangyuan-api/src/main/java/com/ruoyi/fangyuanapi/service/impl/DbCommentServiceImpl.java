package com.ruoyi.fangyuanapi.service.impl;

import java.util.*;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.page.PageConf;
import com.ruoyi.common.utils.PassDemo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.mapper.DbGiveLikeMapper;
import com.ruoyi.fangyuanapi.mapper.DbUserMapper;
import com.ruoyi.fangyuanapi.service.IDbGiveLikeService;
import com.ruoyi.fangyuanapi.service.IDbUserService;
import com.ruoyi.system.domain.DbGiveLike;
import com.ruoyi.system.domain.DbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbCommentMapper;
import com.ruoyi.system.domain.DbComment;
import com.ruoyi.fangyuanapi.service.IDbCommentService;
import com.ruoyi.common.core.text.Convert;

/**
 * 评论Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbCommentServiceImpl implements IDbCommentService 
{
    @Autowired
    private DbCommentMapper dbCommentMapper;

    @Autowired
    private DbUserMapper dbUserMapper;

    @Autowired
    private DbGiveLikeMapper dbGiveLikeMapper;

    /**
     * 查询评论
     * 
     * @param id 评论ID
     * @return 评论
     */
    @Override
    public DbComment selectDbCommentById(Long id)
    {
        return dbCommentMapper.selectDbCommentById(id);
    }

    /**
     * 查询评论列表
     * 
     * @param dbComment 评论
     * @return 评论
     */
    @Override
    public List<DbComment> selectDbCommentList(DbComment dbComment)
    {
        return dbCommentMapper.selectDbCommentList(dbComment);
    }

    /**
     * 新增评论
     * 
     * @param dbComment 评论
     * @return 结果
     */
    @Override
    public int insertDbComment(DbComment dbComment)
    {
        return dbCommentMapper.insertDbComment(dbComment);
    }

    /**
     * 修改评论
     * 
     * @param dbComment 评论
     * @return 结果
     */
    @Override
    public int updateDbComment(DbComment dbComment)
    {
        return dbCommentMapper.updateDbComment(dbComment);
    }

    /**
     * 删除评论对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbCommentByIds(String ids)
    {
        return dbCommentMapper.deleteDbCommentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除评论信息
     * 
     * @param id 评论ID
     * @return 结果
     */
    @Override
    public int deleteDbCommentById(Long id)
    {
        return dbCommentMapper.deleteDbCommentById(id);
    }

    /**
     * 获取动态评论
     * @param dynamicId
     */
    @Override
    public List<Map<String,Object>> getCommentList(Long dynamicId,String userId,Integer currPage) {
        /* 返回十条一级评论 */

        currPage = currPage <= 0 ? 0 :(currPage -1) * PageConf.pageSize;
        List<DbComment> commentList = dbCommentMapper.getCommentList(dynamicId,currPage,PageConf.pageSize);
        ArrayList<Map<String,Object>> result = new ArrayList<>();
        if (StringUtils.isNotEmpty(userId)){

        }
        for (DbComment comment : commentList) {
            /* 拼装数据 */
            Map<String, Object> map = getCommentMap(comment, userId);
            List<Map<String, Object>> maps = getTwoCommentList(comment.getId(), userId, 1, PageConf.pageSize);
            map.put("twoComment",maps);
            result.add(map);
        }
        return result ;
    }

    /**
     * 二级评论
     * @param commentId
     * @param userId
     */
    @Override
    public List<Map<String,Object>> getTwoCommentList(Long commentId, String userId, Integer currPage, Integer pageSize){
        currPage = currPage <= 0 ? 0 :(currPage -1) * PageConf.pageSize;
        List<DbComment> comments = dbCommentMapper.selectDbCommentByParentCommentId(commentId, currPage, pageSize);
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for (DbComment comment : comments) {
            Map<String, Object> map = getCommentMap(comment, userId);
            list.add(map);
        }
        return list;
    }

    /**
     * 新增评论接口
     * @param dynamicId 动态id
     * @param commentId 评论 id
     * @param replyUserId 回复的用户id
     * @param userId 当前用户id
     * @param text 评论的内容
     * @param replyCommentId 回复的评论id
     * @return
     */
    @Override
    public DbComment addComment(Long dynamicId, Long commentId, Long replyUserId,Long replyCommentId, Long userId,String text) {
        DbComment comment = new DbComment();
        DbUser user = dbUserMapper.selectDbUserById(userId);
        comment.setObserverId(user.getId());
        comment.setObserver(user.getNickname());
        comment.setDynamicId(dynamicId);
        comment.setCommentContent(text);
        comment.setCommentTime(new Date());
        if (commentId != null && commentId >0){
            /*此处应查一下后续再改*/
            DbComment dbComment = dbCommentMapper.selectDbCommentById(commentId);
            comment.setParentCommentId(dbComment.getId());
            comment.setParentCommentUserId(dbComment.getObserverId());
            comment.setCommentLevel(1);
        }
        if (replyCommentId != null && replyCommentId >0 && replyUserId != null && replyUserId > 0){
            comment.setReplyCommentId(replyCommentId);
            comment.setReplyCommentUserId(replyUserId);
        }
        int i = dbCommentMapper.insertDbComment(comment);
        return i>0 ? comment : null;
    }

    private Map<String, Object> getCommentMap(DbComment comment,String userId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",comment.getId());//评论id
        map.put("observerId",comment.getObserverId());//评论人id
        map.put("observer",comment.getObserver());//评论人
        map.put("commentTime",comment.getCommentTime());//评论时间
        map.put("commentContent",comment.getCommentContent());//评论内容
        DbUser dbUser = dbUserMapper.selectDbUserById(comment.getObserverId());
        map.put("avatar",dbUser.getAvatar());
        map.put("nickName",dbUser.getNickname());
        if (StringUtils.isNotEmpty(userId)){
            DbGiveLike dbGiveLike =  dbGiveLikeMapper.selectDbGiveLikeByUserIdAndCommentId(Long.valueOf(userId),comment.getId());
            if (dbGiveLike != null) {
                map.put("isGiveLive", 1);
            }
        }
        map.put("likeCount",comment.getPraiseNum());
        if (comment.getReplyCommentUserId() != null){
            DbUser user = dbUserMapper.selectDbUserById(comment.getReplyCommentUserId());
            map.put("isReply",1);
            map.put("replyNickName",user.getNickname());
            map.put("replyUserId",user.getId());
        }
        return map;
    }

    public static void  main(String[] args){
        String s = PassDemo.gen(1L);
        System.out.println(Constants.CURRENT_ID);
        System.out.println(s);
    }

}
