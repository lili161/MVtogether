package com.Rsoft.mvtogether.Utils;

import com.Rsoft.mvtogether.Dao.RoomDao;
import com.Rsoft.mvtogether.Entity.Viewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author LR
 * @date 2021/2/19
 * 交流可加qq群 1027263551
 **/
@Service
public class roomUtils {
    @Autowired
    private RoomDao roomDao;
    //查看名字是否预订过
    // 1== 预定过
    // 0== 未预定过
   public  int checkIsBookedByName(String Name){
       Viewer viewerOwner = roomDao.getInfoByOwnerName(Name);
       Viewer viewercustomer = roomDao.getInfoByCustomerName(Name);
        if(viewerOwner==null&&viewerOwner==null){
            return 0;
        }else
           return 1;
   }
}
