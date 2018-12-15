package server.mapper;

import org.apache.ibatis.annotations.Param;
import server.aggregates.ProductAggregate;

import java.util.List;

// There is no sql queries defined in java files, they are all in XML files.
// First look in /resources/mybatis-config.xml
// Afterwards look in /resources/mapper-definition/catalog-mapper.xml
public interface CatalogMapper {

    // These annotated parameters can be used in the sql queries
    int insertProduct(@Param("product_name") String name, @Param("product_price") int price);

    int deleteProduct(@Param("id") long id);

    List<ProductAggregate> getAllProducts();
}
