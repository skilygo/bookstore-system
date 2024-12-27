//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lty.bookstore.system.backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public JsonUtils() {
    }

    public static String toJson(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static String toJsonIfPresent(Object o) {
        return o == null ? "" : toJson(o);
    }


    public static <T> T toObjectIfPresent(String json, Class<T> clazz) {
        try {
            return StringUtils.isNotEmpty(json) ? MAPPER.readValue(json, clazz) : null;
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }
}
