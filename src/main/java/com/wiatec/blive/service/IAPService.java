package com.wiatec.blive.service;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 */
@Service
public class IAPService {


    private final String VERIFY_RECEIPT_URL_PRODUCT = "https://buy.itunes.apple.com/verifyReceipt";
    private final String VERIFY_RECEIPT_URL_SANDBOX = "https://sandbox.itunes.apple.com/verifyReceipt";

    private final Logger logger = LoggerFactory.getLogger(IAPService.class);

    public ResultInfo verify(String encodeStr){


        return ResultMaster.success();
    }

}
