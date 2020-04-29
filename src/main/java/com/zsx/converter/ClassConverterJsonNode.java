package com.zsx.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.reflections.ReflectionUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassConverterJsonNode {


    @SuppressWarnings("unchecked")
    private static <T> JsonNode removeTransientFields(JsonNode jsonNode, Class<T> clazz) {
        if (jsonNode == null || jsonNode.size() == 0) {
            return jsonNode;
        }
        // 获取类中所有带 @Transient 注解的属性
        Set<Field> transientFields = ReflectionUtils.getAllFields(clazz,
                field -> {
                    assert field != null;
                    return (field.getAnnotation(Transient.class) != null);
                });
        // 没有需要排除的临时属性
        if (CollectionUtils.isEmpty(transientFields)) {
            return jsonNode;
        }
        ObjectMapper mapper = new ObjectMapper();
        if (jsonNode instanceof ObjectNode) {
            return getRemovedJsonNode(jsonNode, transientFields);
        } else if (jsonNode instanceof ArrayNode) {
            List<T> list = mapper.convertValue(jsonNode, new TypeReference<>() {});
            List<Map<String, Object>> removedList = list.stream().map(entityT -> {
                Map<String, Object> mapT = mapper.convertValue(entityT, new TypeReference<>() {});
                // 移除所有带@Transient注解的属性
                transientFields.forEach(field -> mapT.remove(field.getName()));
                return mapT;
            }).collect(Collectors.toList());
            return mapper.valueToTree(removedList);
        }
        // 其他类型直接返回
        return jsonNode;
    }

    private static JsonNode getRemovedJsonNode(JsonNode jsonNode, Set<Field> transientFields) {
        ObjectMapper mapper = new ObjectMapper();
        // 将JsonNode转化成Map
        Map<String, Object> map = mapper.convertValue(jsonNode, new TypeReference<>() {});
        // 移除所有带@Transient注解的属性
        transientFields.forEach(field -> map.remove(field.getName()));
        return mapper.valueToTree(map);
    }
}
