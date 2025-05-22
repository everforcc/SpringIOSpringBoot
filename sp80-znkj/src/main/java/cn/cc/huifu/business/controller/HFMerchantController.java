package cn.cc.huifu.business.controller;

import cn.cc.huifu.business.merchant.indv.ZnHFMerchantIndvRegist;
import cn.cc.huifu.business.merchant.indv.dto.HuiFuMerchantIndvDto;
import cn.cc.huifu.business.merchant.open.ZnHFMerchantIndvOpen;
import cn.cc.huifu.business.merchant.open.dto.HuiFuMerchantBusiOpenDto;
import cn.cc.huifu.business.merchant.open.modify.ZnHFMerchantIndvModify;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hf/merchant")
public class HFMerchantController {


    public static void indv() {
        HuiFuMerchantIndvDto huiFuMerchantIndvDto = new HuiFuMerchantIndvDto();

        huiFuMerchantIndvDto.setUpperHuifuId("6666000151772004");
        huiFuMerchantIndvDto.setRegName("周森龙");
        huiFuMerchantIndvDto.setShortName("周森龙");
        huiFuMerchantIndvDto.setMcc("5812");
        huiFuMerchantIndvDto.setSceneType("OFFLINE");
        huiFuMerchantIndvDto.setProvId("410000");
        huiFuMerchantIndvDto.setAreaId("411000");
        huiFuMerchantIndvDto.setDistrictId("411081");
        huiFuMerchantIndvDto.setDetailAddr("河南省禹州市夏都办颖河大街老电视对面禹兴美食");
        huiFuMerchantIndvDto.setLegalCertNo("411081200201289157");
        huiFuMerchantIndvDto.setLegalCertBeginDate("20181214");
        huiFuMerchantIndvDto.setLegalCertValidityType("0");
        huiFuMerchantIndvDto.setLegalCertEndDate("20281214");
        huiFuMerchantIndvDto.setLegalAddr("河南省禹州市夏都办苗场村4组");
        // 国徽面
        huiFuMerchantIndvDto.setLegalCertBackPic("4610bef1-2df1-3f0a-ba22-32a4f1e9e50c");
        // 正面
        huiFuMerchantIndvDto.setLegalCertFrontPic("76ec9902-bbb5-37b3-9b64-0ea4e02124e2");
//        huiFuMerchantIndv.setOccupation();
        huiFuMerchantIndvDto.setContactMobileNo("17597998777");
        huiFuMerchantIndvDto.setContactEmail("1396134381@qq.com");
        HuiFuMerchantIndvDto.CardInfo cardInfo = new HuiFuMerchantIndvDto.CardInfo();

        cardInfo.setCardName("周森龙");
        cardInfo.setCardNo("6215340301446344060");
        cardInfo.setAreaId( "411000");
        cardInfo.setCertType("00");
        cardInfo.setCertNo("411081200201289157");
        cardInfo.setCertValidityType("0");
        cardInfo.setCertBeginDate("20181214");
        cardInfo.setCertEndDate("20281214");
        cardInfo.setMp("17597998777");
        huiFuMerchantIndvDto.setCardInfo(cardInfo);
        // 结算卡正面
        huiFuMerchantIndvDto.setSettleCardFrontPic("a5ca3709-22a6-3376-b0e0-2fdf51c23ee2");
//线下经营-门头照 F22
        huiFuMerchantIndvDto.setStoreHeaderPic("c71d64fe-0329-3282-9b84-a9deedf32a31");
//线下经营-内景照 F24
        huiFuMerchantIndvDto.setStoreIndoorPic("be544e2e-6257-3736-928c-6516a567437c");
//线下经营-收银台 F105
        huiFuMerchantIndvDto.setStoreCashierDeskPic("7cf4f5ce-4d00-321b-8bf7-b1d5770f9607");
        ZnHFMerchantIndvRegist.flowIndv(huiFuMerchantIndvDto.toMap());
    }

    /**
     * 个人商户进件
     */
    @PostMapping("/indv")
    public Map<String, Object> indv(HuiFuMerchantIndvDto huiFuMerchantIndvDto) {
        return ZnHFMerchantIndvRegist.flowIndv(huiFuMerchantIndvDto.toMap());
    }

    public static void main(String[] args) {

        HuiFuMerchantBusiOpenDto huiFuMerchantBusiOpenDto = new HuiFuMerchantBusiOpenDto();

        huiFuMerchantBusiOpenDto.setHuifuId("6666000168672654");
        huiFuMerchantBusiOpenDto.setUpperHuifuId("6666000151772004");

        HuiFuMerchantBusiOpenDto.AgreementInfo agreement_info = new HuiFuMerchantBusiOpenDto.AgreementInfo();
        agreement_info.setAgreementType("0");
        huiFuMerchantBusiOpenDto.setAgreementInfo(agreement_info);

        HuiFuMerchantBusiOpenDto.SignUserInfo signUserInfo = new HuiFuMerchantBusiOpenDto.SignUserInfo();
        signUserInfo.setType("LEGAL");
        signUserInfo.setName("周森龙");
        signUserInfo.setCertNo("411081200201289157");
        signUserInfo.setMobileNo("17597998777");
        huiFuMerchantBusiOpenDto.setSignUserInfo(signUserInfo);


        List<HuiFuMerchantBusiOpenDto.AliConf> aliConfList = new ArrayList<>();
        HuiFuMerchantBusiOpenDto.AliConf aliConf = new HuiFuMerchantBusiOpenDto.AliConf();
        aliConf.setPayScene("1");//
        aliConf.setFeeRate("0.25");
        aliConf.setSwitchState("1");
        aliConf.setFeeMinAmt("0.01");

        HuiFuMerchantBusiOpenDto.AliConf aliConf2 = new HuiFuMerchantBusiOpenDto.AliConf();
        aliConf2.setPayScene("2");
        aliConf2.setFeeRate("0.65");
        aliConf2.setSwitchState("1");
        aliConf2.setFeeMinAmt("0.01");
        aliConfList.add(aliConf);
        aliConfList.add(aliConf2);
        huiFuMerchantBusiOpenDto.setAliConfList(aliConfList);

        List<HuiFuMerchantBusiOpenDto.WxConf> wxConfList = new ArrayList<>();
        HuiFuMerchantBusiOpenDto.WxConf wxConf = new HuiFuMerchantBusiOpenDto.WxConf();
        wxConf.setPayScene("1");
        wxConf.setFeeRate("0.25");
        wxConf.setSwitchState("1");
        wxConf.setFeeMinAmt("0.01");
        wxConfList.add(wxConf);
        huiFuMerchantBusiOpenDto.setWxConfList(wxConfList);

        HuiFuMerchantBusiOpenDto.AgreementInfo agreementInfo = new HuiFuMerchantBusiOpenDto.AgreementInfo();
        agreementInfo.setAgreementType("0");
        huiFuMerchantBusiOpenDto.setAgreementInfo(agreementInfo);
        ZnHFMerchantIndvOpen.flowBusiOpen(huiFuMerchantBusiOpenDto.toMap());
    }

    /**
     * 商户业务开通
     */
    @PostMapping("/open")
    public Map<String, Object> open(HuiFuMerchantBusiOpenDto huiFuMerchantBusiOpenDto) {
        return ZnHFMerchantIndvOpen.flowBusiOpen(huiFuMerchantBusiOpenDto.toMap());
    }

    /**
     * 商户业务更新
     */
    @PostMapping("/openModify")
    public Map<String, Object> openModify(HuiFuMerchantBusiOpenDto huiFuMerchantBusiOpenDto) {
        return ZnHFMerchantIndvModify.flowOpenModify(huiFuMerchantBusiOpenDto.toMap());
    }

}
