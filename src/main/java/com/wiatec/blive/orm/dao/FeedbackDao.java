package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.FeedbackInfo;
import com.wiatec.blive.orm.pojo.UpgradeInfo;
import org.springframework.stereotype.Repository;

/**
 * @author patrick
 */
@Repository
public interface FeedbackDao {

    /**
     * insert a new feedback into table of bvision_feedback
     * @return 1->success, 0->failure
     */
    int insertOne(FeedbackInfo feedbackInfo);
}
