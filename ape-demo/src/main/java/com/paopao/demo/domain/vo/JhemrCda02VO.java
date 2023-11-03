package com.paopao.demo.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 门（急）诊病历
 * @TableName jhemr_cda02
 */
@TableName(value ="jhemr_cda02")
@Data
public class JhemrCda02VO implements Serializable {


    /**
     * 主键
     */
    @TableField(value = "PK")
    @Excel(name = "PK")
    private String pk;

    /**
     * 就诊序号
     */
    @TableField(value = "VISIT_ID")
    @Excel(name = "就诊序号")
    private String visitId;

    /**
     * 文档创作者工号
     */
    @TableField(value = "CREATE_PERSON_CODE")
    @Excel(name = "文档创作者工号")
    private String createPersonCode;

    /**
     * 文档创作者
     */
    @TableField(value = "CREATE_PERSON_NAME")
    @Excel(name = "文档创作者")
    private String createPersonName;

    /**
     * 文档创作日期时间
     */
    @TableField(value = "CREATE_TIME")
    @Excel(name = "文档创作日期时间")
    private String createTime;

    /**
     * 门（急）诊号
     */
    @TableField(value = "PATIENT_ID")
    @Excel(name = "门（急）诊号")
    private String patientId;

    /**
     * 身份证号
     */
    @TableField(value = "ID_CARD_NO")
    @Excel(name = "身份证号")
    private String idCardNo;

    /**
     * 患者姓名
     */
    @TableField(value = "PATIENT_NAME")
    @Excel(name = "患者姓名")
    private String patientName;

    /**
     * 患者性别
     */
    @TableField(value = "PATIENT_SEX")
    @Excel(name = "患者性别")
    private String patientSex;

    /**
     * 患者出生日期
     */
    @TableField(value = "PATIENT_DATE_OF_BIRTH")
    @Excel(name = "患者出生日期")
    private String patientDateOfBirth;

    /**
     * 患者年龄
     */
    @TableField(value = "PATIENT_AGE")
    @Excel(name = "患者年龄")
    private String patientAge;

    /**
     * 科室名称
     */
    @TableField(value = "PATIENT_DEPT_NAME")
    @Excel(name = "科室名称")
    private String patientDeptName;

    /**
     * 医疗机构组织机构标识
     */
    @TableField(value = "PATIENT_MEDICAL_CODE")
    @Excel(name = "医疗机构组织机构标识")
    private String patientMedicalCode;

    /**
     * 医疗机构名称
     */
    @TableField(value = "PATIENT_MEDICAL_NAME")
    @Excel(name = "医疗机构名称")
    private String patientMedicalName;

    /**
     * 就诊日期时间
     */
    @TableField(value = "VISIT_DATETIME")
    @Excel(name = "就诊日期时间")
    private String visitDatetime;

    /**
     * 保管机构代码
     */
    @TableField(value = "CORG_CODE")
    @Excel(name = "保管机构代码")
    private String corgCode;

    /**
     * 保管机构名称
     */
    @TableField(value = "CORG_NAME")
    @Excel(name = "保管机构名称")
    private String corgName;

    /**
     * 责任医师工号
     */
    @TableField(value = "RESPONSIBLE_PHYSICIAN_CODE")
    @Excel(name = "责任医师工号")
    private String responsiblePhysicianCode;

    /**
     * 责任医师姓名
     */
    @TableField(value = "RESPONSIBLE_PHYSICIAN_NAME")
    @Excel(name = "责任医师姓名")
    private String responsiblePhysicianName;

    /**
     * 责任医师签名日期时间
     */
    @TableField(value = "RESPONSIBLE_PHYSICIAN_SIGN_TIME")
    @Excel(name = "责任医师签名日期时间")
    private String responsiblePhysicianSignTime;

    /**
     * 过敏史标志
     */
    @TableField(value = "ALLERGIC_HISTORY_FLAG")
    @Excel(name = "过敏史标志")
    private String allergicHistoryFlag;

    /**
     * 过敏史详细描述
     */
    @TableField(value = "ALLERGIC_HISTORY")
    @Excel(name = "过敏史详细描述")
    private String allergicHistory;

    /**
     * 主诉
     */
    @TableField(value = "CHIEF_COMPLAINT")
    @Excel(name = "主诉")
    private String chiefComplaint;

    /**
     * 现病史
     */
    @TableField(value = "PRESENT_HISTORY")
    @Excel(name = "现病史")
    private String presentHistory;

    /**
     * 既往史
     */
    @TableField(value = "PAST_HISTORY")
    @Excel(name = "既往史")
    private String pastHistory;

    /**
     * 一般状况检查结果
     */
    @TableField(value = "COMMOM_CONDTION_EXAM_RESULTS")
    @Excel(name = "一般状况检查结果")
    private String commomCondtionExamResults;

    /**
     * 辅助检查项目
     */
    @TableField(value = "EXAM_POSITIVE_ITEM")
    @Excel(name = "辅助检查项目")
    private String examPositiveItem;

    /**
     * 辅助检查结果
     */
    @TableField(value = "EXAM_POSITIVERESULT")
    @Excel(name = "辅助检查结果")
    private String examPositiveresult;

    /**
     * 初诊标志名称
     */
    @TableField(value = "FIRST_VISIT_FLAG_NAME")
    @Excel(name = "初诊标志名称")
    private String firstVisitFlagName;

    /**
     * 中医“四诊”观察结果
     */
    @TableField(value = "CHINESEFOUR_OBSERVATION_DESCRIPT")
    @Excel(name = "中医“四诊”观察结果")
    private String chinesefourObservationDescript;

    /**
     * 西医诊断编码
     */
    @TableField(value = "WESTERN_MEDICINE_CODE")
    @Excel(name = "西医诊断编码")
    private String westernMedicineCode;

    /**
     * 西医诊断名称
     */
    @TableField(value = "WESTERN_MEDICINE_NAME")
    @Excel(name = "西医诊断名称")
    private String westernMedicineName;

    /**
     * 中医病名代码
     */
    @TableField(value = "TCM_DISEASE_CODE")
    @Excel(name = "中医病名代码")
    private String tcmDiseaseCode;

    /**
     * 中医病名名称
     */
    @TableField(value = "TCM_DISEASE_NAME")
    @Excel(name = "中医病名名称")
    private String tcmDiseaseName;

    /**
     * 中医证候代码
     */
    @TableField(value = "TCM_SYNDROME_CODE")
    @Excel(name = "中医证候代码")
    private String tcmSyndromeCode;

    /**
     * 中医证候名称
     */
    @TableField(value = "TCM_SYNDROME_NAME")
    @Excel(name = "中医证候名称")
    private String tcmSyndromeName;

    /**
     * 辨证依据
     */
    @TableField(value = "SYNDROME_DIFF_AND_TREA")
    @Excel(name = "辨证依据")
    private String syndromeDiffAndTrea;

    /**
     * 治则治法
     */
    @TableField(value = "THERAPEUTIC_PRINCIPLE")
    @Excel(name = "治则治法")
    private String therapeuticPrinciple;

    /**
     * 医嘱项目类型代码
     */
    @TableField(value = "ORDER_CLASS_CODE")
    @Excel(name = "医嘱项目类型代码")
    private String orderClassCode;

    /**
     * 医嘱项目类型
     */
    @TableField(value = "ORDER_CLASS_NAME")
    @Excel(name = "医嘱项目类型")
    private String orderClassName;

    /**
     * 医嘱计划开始日期时间
     */
    @TableField(value = "ORDER_PLAN_BEGIN_TIME")
    @Excel(name = "医嘱计划开始日期时间")
    private String orderPlanBeginTime;

    /**
     * 医嘱计划结束日期时间
     */
    @TableField(value = "ORDER_PLAN_END_TIME")
    @Excel(name = "医嘱计划结束日期时间")
    private String orderPlanEndTime;

    /**
     * 医嘱执行日期时间
     */
    @TableField(value = "ORDER_PRESC_TIME")
    @Excel(name = "医嘱执行日期时间")
    private String orderPrescTime;

    /**
     * 医嘱执行者
     */
    @TableField(value = "ORDER_PRESC_SIGN")
    @Excel(name = "医嘱执行者")
    private String orderPrescSign;

    /**
     * 医嘱执行科室名称
     */
    @TableField(value = "ORDER_PRESC_DEPT_NAME")
    @Excel(name = "医嘱执行科室名称")
    private String orderPrescDeptName;

    /**
     * 医嘱开立日期时间
     */
    @TableField(value = "ORDER_TIME")
    @Excel(name = "医嘱开立日期时间")
    private String orderTime;

    /**
     * 医嘱开立者签名
     */
    @TableField(value = "ORDER_DOCTOR_SIGN")
    @Excel(name = "医嘱开立者签名")
    private String orderDoctorSign;

    /**
     * 医嘱开立科室
     */
    @TableField(value = "ORDER_DEPT_NAME")
    @Excel(name = "医嘱开立科室")
    private String orderDeptName;

    /**
     * 医嘱审核人
     */
    @TableField(value = "ORDER_CONFIRMER_SIGN")
    @Excel(name = "医嘱审核人")
    private String orderConfirmerSign;

    /**
     * 医嘱审核日期时间
     */
    @TableField(value = "ORDER_CONFIRM_TIME")
    @Excel(name = "医嘱审核日期时间")
    private String orderConfirmTime;

    /**
     * 医嘱取消日期时间
     */
    @TableField(value = "ORDER_CANCEL_TIME")
    @Excel(name = "医嘱取消日期时间")
    private String orderCancelTime;

    /**
     * 取消医嘱者签名
     */
    @TableField(value = "CANCEL_DOCTOR_SIGN")
    @Excel(name = "取消医嘱者签名")
    private String cancelDoctorSign;

    /**
     * 医嘱备注信息
     */
    @TableField(value = "ORDER_NOTE")
    @Excel(name = "医嘱备注信息")
    private String orderNote;

    /**
     * 医嘱执行状态
     */
    @TableField(value = "ORDER_PRESC_STATUS")
    @Excel(name = "医嘱执行状态")
    private String orderPrescStatus;

    /**
     * 来源库表名,格式为HBase表名格式
     */
    @TableField(value = "JHDL_SRC_TAB")
    private String jhdlSrcTab;

}