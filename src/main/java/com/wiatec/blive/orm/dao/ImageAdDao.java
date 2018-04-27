package com.wiatec.blive.orm.dao;


import com.wiatec.blive.orm.pojo.ImageAdInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface ImageAdDao {

    List<ImageAdInfo> selectByPosition(int position);
}
