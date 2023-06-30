package com.FDSC.utils;

import org.apache.ibatis.jdbc.SQL;

public class SqlProvider {
    public String search(@io.lettuce.core.dynamic.annotation.Param("tagId") Long tagId, @io.lettuce.core.dynamic.annotation.Param("sort") String sort) {
        return new SQL() {{
            SELECT("s.*, t.id AS tag_id, t.tag_name");
            FROM("story AS s");
            LEFT_OUTER_JOIN("story_tag AS s_t ON s_t.story_id = s.id");
            LEFT_OUTER_JOIN("tag AS t ON s_t.tag_id = t.id");
            WHERE("#{tagId} = -1 or s.id IN (SELECT DISTINCT st.story_id FROM story_tag AS st INNER JOIN tag AS t ON st.tag_id = t.id WHERE #{tagId} = t.id)");
            GROUP_BY("s.id, t.id");
            ORDER_BY(sort + " DESC", "update_time DESC, tag_id");
        }}.toString();
    }
}
