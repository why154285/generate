package ${business_package_name}.dao;

import ${business_package_name}.entity.${table_name};
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 描述：${table_annotation} Repository接口
 * @author ${author}
 * @date ${date}
 */
public interface ${table_name}Repository extends JpaRepository<${table_name}, ${id_type}> {

}