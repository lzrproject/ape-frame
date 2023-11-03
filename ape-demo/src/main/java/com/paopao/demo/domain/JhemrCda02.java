package com.paopao.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 门（急）诊病历
 * @TableName jhemr_cda02
 */
@TableName(value ="jhemr_cda02")
@Data
public class JhemrCda02 implements Serializable {
    /**
     * 
     */
    @TableId(value = "JHDL_ROWKEY")
    private Long jhdlRowkey;

    /**
     * 主键
     */
    @TableField(value = "PK")
    private String pk;

    /**
     * 就诊序号
     */
    @TableField(value = "VISIT_ID")
    private String visitId;

    /**
     * 文档创作者工号
     */
    @TableField(value = "CREATE_PERSON_CODE")
    private String createPersonCode;

    /**
     * 文档创作者
     */
    @TableField(value = "CREATE_PERSON_NAME")
    private String createPersonName;

    /**
     * 文档创作日期时间
     */
    @TableField(value = "CREATE_TIME")
    private String createTime;

    /**
     * 门（急）诊号
     */
    @TableField(value = "PATIENT_ID")
    private String patientId;

    /**
     * 电子申请单编号
     */
    @TableField(value = "APPLY_NO")
    private String applyNo;

    /**
     * 身份证号
     */
    @TableField(value = "ID_CARD_NO")
    private String idCardNo;

    /**
     * 患者姓名
     */
    @TableField(value = "PATIENT_NAME")
    private String patientName;

    /**
     * 患者性别
     */
    @TableField(value = "PATIENT_SEX")
    private String patientSex;

    /**
     * 患者出生日期
     */
    @TableField(value = "PATIENT_DATE_OF_BIRTH")
    private String patientDateOfBirth;

    /**
     * 患者年龄
     */
    @TableField(value = "PATIENT_AGE")
    private String patientAge;

    /**
     * 科室标识
     */
    @TableField(value = "PATIENT_DEPT_ID")
    private String patientDeptId;

    /**
     * 科室名称
     */
    @TableField(value = "PATIENT_DEPT_NAME")
    private String patientDeptName;

    /**
     * 医疗机构组织机构标识
     */
    @TableField(value = "PATIENT_MEDICAL_CODE")
    private String patientMedicalCode;

    /**
     * 医疗机构名称
     */
    @TableField(value = "PATIENT_MEDICAL_NAME")
    private String patientMedicalName;

    /**
     * 就诊日期时间
     */
    @TableField(value = "VISIT_DATETIME")
    private String visitDatetime;

    /**
     * 保管机构代码
     */
    @TableField(value = "CORG_CODE")
    private String corgCode;

    /**
     * 保管机构名称
     */
    @TableField(value = "CORG_NAME")
    private String corgName;

    /**
     * 责任医师工号
     */
    @TableField(value = "RESPONSIBLE_PHYSICIAN_CODE")
    private String responsiblePhysicianCode;

    /**
     * 责任医师姓名
     */
    @TableField(value = "RESPONSIBLE_PHYSICIAN_NAME")
    private String responsiblePhysicianName;

    /**
     * 责任医师签名日期时间
     */
    @TableField(value = "RESPONSIBLE_PHYSICIAN_SIGN_TIME")
    private String responsiblePhysicianSignTime;

    /**
     * 父文档标识符
     */
    @TableField(value = "PARENT_DOCUMENT_ID")
    private String parentDocumentId;

    /**
     * 文档集序列号
     */
    @TableField(value = "PARENT_DOCUMENT_CODE")
    private String parentDocumentCode;

    /**
     * 文档版本号
     */
    @TableField(value = "PARENT_DOCUMENT_VERSION")
    private String parentDocumentVersion;

    /**
     * 过敏史标志
     */
    @TableField(value = "ALLERGIC_HISTORY_FLAG")
    private String allergicHistoryFlag;

    /**
     * 过敏史详细描述
     */
    @TableField(value = "ALLERGIC_HISTORY")
    private String allergicHistory;

    /**
     * 主诉
     */
    @TableField(value = "CHIEF_COMPLAINT")
    private String chiefComplaint;

    /**
     * 现病史
     */
    @TableField(value = "PRESENT_HISTORY")
    private String presentHistory;

    /**
     * 既往史
     */
    @TableField(value = "PAST_HISTORY")
    private String pastHistory;

    /**
     * 一般状况检查结果
     */
    @TableField(value = "COMMOM_CONDTION_EXAM_RESULTS")
    private String commomCondtionExamResults;

    /**
     * 辅助检查项目
     */
    @TableField(value = "EXAM_POSITIVE_ITEM")
    private String examPositiveItem;

    /**
     * 辅助检查结果
     */
    @TableField(value = "EXAM_POSITIVERESULT")
    private String examPositiveresult;

    /**
     * 初诊标志名称
     */
    @TableField(value = "FIRST_VISIT_FLAG_NAME")
    private String firstVisitFlagName;

    /**
     * 中医“四诊”观察结果
     */
    @TableField(value = "CHINESEFOUR_OBSERVATION_DESCRIPT")
    private String chinesefourObservationDescript;

    /**
     * 西医诊断编码
     */
    @TableField(value = "WESTERN_MEDICINE_CODE")
    private String westernMedicineCode;

    /**
     * 西医诊断名称
     */
    @TableField(value = "WESTERN_MEDICINE_NAME")
    private String westernMedicineName;

    /**
     * 中医病名代码
     */
    @TableField(value = "TCM_DISEASE_CODE")
    private String tcmDiseaseCode;

    /**
     * 中医病名名称
     */
    @TableField(value = "TCM_DISEASE_NAME")
    private String tcmDiseaseName;

    /**
     * 中医证候代码
     */
    @TableField(value = "TCM_SYNDROME_CODE")
    private String tcmSyndromeCode;

    /**
     * 中医证候名称
     */
    @TableField(value = "TCM_SYNDROME_NAME")
    private String tcmSyndromeName;

    /**
     * 辨证依据
     */
    @TableField(value = "SYNDROME_DIFF_AND_TREA")
    private String syndromeDiffAndTrea;

    /**
     * 治则治法
     */
    @TableField(value = "THERAPEUTIC_PRINCIPLE")
    private String therapeuticPrinciple;

    /**
     * 医嘱项目类型代码
     */
    @TableField(value = "ORDER_CLASS_CODE")
    private String orderClassCode;

    /**
     * 医嘱项目类型
     */
    @TableField(value = "ORDER_CLASS_NAME")
    private String orderClassName;

    /**
     * 医嘱计划开始日期时间
     */
    @TableField(value = "ORDER_PLAN_BEGIN_TIME")
    private String orderPlanBeginTime;

    /**
     * 医嘱计划结束日期时间
     */
    @TableField(value = "ORDER_PLAN_END_TIME")
    private String orderPlanEndTime;

    /**
     * 医嘱执行日期时间
     */
    @TableField(value = "ORDER_PRESC_TIME")
    private String orderPrescTime;

    /**
     * 医嘱执行者
     */
    @TableField(value = "ORDER_PRESC_SIGN")
    private String orderPrescSign;

    /**
     * 医嘱执行科室名称
     */
    @TableField(value = "ORDER_PRESC_DEPT_NAME")
    private String orderPrescDeptName;

    /**
     * 医嘱开立日期时间
     */
    @TableField(value = "ORDER_TIME")
    private String orderTime;

    /**
     * 医嘱开立者签名
     */
    @TableField(value = "ORDER_DOCTOR_SIGN")
    private String orderDoctorSign;

    /**
     * 医嘱开立科室
     */
    @TableField(value = "ORDER_DEPT_NAME")
    private String orderDeptName;

    /**
     * 医嘱审核人
     */
    @TableField(value = "ORDER_CONFIRMER_SIGN")
    private String orderConfirmerSign;

    /**
     * 医嘱审核日期时间
     */
    @TableField(value = "ORDER_CONFIRM_TIME")
    private String orderConfirmTime;

    /**
     * 医嘱取消日期时间
     */
    @TableField(value = "ORDER_CANCEL_TIME")
    private String orderCancelTime;

    /**
     * 取消医嘱者签名
     */
    @TableField(value = "CANCEL_DOCTOR_SIGN")
    private String cancelDoctorSign;

    /**
     * 医嘱备注信息
     */
    @TableField(value = "ORDER_NOTE")
    private String orderNote;

    /**
     * 医嘱执行状态
     */
    @TableField(value = "ORDER_PRESC_STATUS")
    private String orderPrescStatus;

    /**
     * 来源库表名,格式为HBase表名格式
     */
    @TableField(value = "JHDL_SRC_TAB")
    private String jhdlSrcTab;

    /**
     * 创建时间
     */
    @TableField(value = "JHDL_CREATED_AT")
    private Date jhdlCreatedAt;

    /**
     * 更新时间
     */
    @TableField(value = "JHDL_UPDATED_AT")
    private Date jhdlUpdatedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}