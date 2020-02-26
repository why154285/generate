package ${business_package_name}.entity;

import javax.persistence.*;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

<#if model_column?exists>
    <#list model_column as model>
        <#if (model.columnType?lower_case = 'datetime' || model.columnType?lower_case = 'date' || model.columnType?lower_case = 'time' || model.columnType?lower_case = 'year' || model.columnType?lower_case = 'timestamp')>
import java.util.Date;

        </#if>
        <#if (model.columnType?lower_case = 'decimal')>
import java.math.BigDecimal;

        </#if>
    </#list>
</#if>
/**
 * 描述：${table_annotation}模型
 *
 * @author ${author}
 * @date ${date}
 */
@Entity
@Table(name = "${table_name_small}")
@EntityListeners(AuditingEntityListener.class)
<#--  @Where(clause = "status > '0'")  -->
<#--  @Inheritance(strategy= InheritanceType.SINGLE_TABLE)  -->
@Data
public class ${table_name} {

<#if model_column?exists>
    <#list model_column as model>
    /**
     * ${model.columnComment!}
     */
        <#if (model.columnName?lower_case = 'id')>
            <#if (model.columnType?lower_case = 'bigint')>
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "${model.columnName}", columnDefinition = "${model.columnType}")
    private Long ${model.changeColumnName?uncap_first};
            <#else >
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "${model.columnName}", columnDefinition = "${model.columnType}")
    private Integer ${model.changeColumnName?uncap_first};
            </#if>
        <#else>
            <#if (model.columnType?lower_case = 'tinyint' || model.columnType?lower_case = 'bit')>
    @Column(name = "${model.columnName}", columnDefinition = "${model.columnType}")
    private Boolean ${model.changeColumnName?uncap_first};
            <#elseif (model.columnType?lower_case = 'smallint' || model.columnType?lower_case = 'mediumint' || model.columnType?lower_case = 'int' || model.columnType?lower_case = 'integer')>
    @Column(name = "${model.columnName}", columnDefinition = "${model.columnType}")
    private Integer ${model.changeColumnName?uncap_first};
            <#elseif (model.columnType?lower_case = 'bigint')>
    @Column(name = "${model.columnName}", columnDefinition = "${model.columnType}")
    private Long ${model.changeColumnName?uncap_first};
            <#elseif (model.columnType?lower_case = 'float')>
    @Column(name = "${model.columnName}", columnDefinition = "${model.columnType}")
    private Float ${model.changeColumnName?uncap_first};
            <#elseif (model.columnType?lower_case = 'double')>
    @Column(name = "${model.columnName}", columnDefinition = "${model.columnType}")
    private Double ${model.changeColumnName?uncap_first};
            <#elseif (model.columnType?lower_case = 'decimal')>
    @Column(name = "${model.columnName}", columnDefinition = "${model.columnType}")
    private BigDecimal ${model.changeColumnName?uncap_first};
            <#elseif (model.columnType?lower_case = 'varchar' || model.columnType?lower_case = 'text')>
    @Column(name = "${model.columnName}", columnDefinition = "VARCHAR(${model.charOctetLength})")
    private String ${model.changeColumnName?uncap_first};
            <#elseif model.columnType?lower_case = 'datetime' || model.columnType?lower_case = 'date' || model.columnType?lower_case = 'time' || model.columnType?lower_case = 'year' || model.columnType?lower_case = 'timestamp'>
    @Column(name = "${model.columnName}", columnDefinition = "${model.columnType}")
    private Date ${model.changeColumnName?uncap_first};
            <#else>
            </#if>
        </#if>
    </#list>
</#if>
}