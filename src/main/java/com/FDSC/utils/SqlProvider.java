package com.FDSC.utils;

import org.apache.ibatis.jdbc.SQL;

public class SqlProvider {
    public String search(@io.lettuce.core.dynamic.annotation.Param("tagId") Long tagId,
                         @io.lettuce.core.dynamic.annotation.Param("sort") String sort,
                         @io.lettuce.core.dynamic.annotation.Param("page") Integer page) {
        SQL sql1 = new SQL()
                .SELECT("s.id AS story_id, s.user_id, s.total_like, s.total_collection, s.total_comment, s.story_name, " +
                        "s.introduce, s.cover_url, s.create_time, s.update_time, tag.id AS tag_id, tag.tag_name")
                .FROM(String.format("(SELECT * FROM story ORDER BY update_time DESC LIMIT 15 OFFSET %d) AS s", (page - 1) * 15))
                .LEFT_OUTER_JOIN("story_tag st ON s.id = st.story_id")
                .LEFT_OUTER_JOIN("tag ON tag.id = st.tag_id")
                .ORDER_BY(String.format("%s DESC, update_time DESC, tag_id ASC", sort));

        SQL sql2 = new SQL()
                .SELECT("s.id AS story_id, s.user_id, s.total_like, s.total_collection, s.total_comment, s.story_name, " +
                        "s.introduce, s.cover_url, s.create_time, s.update_time, t.id AS tag_id, t.tag_name")
                .FROM(String.format("(SELECT * FROM story t1 WHERE EXISTS (" +
                        "SELECT * FROM (" +
                        "SELECT s.id, t.id AS tag_id " +
                        "FROM story s " +
                        "JOIN story_tag st ON s.id = st.story_id " +
                        "JOIN tag t ON t.id = st.tag_id) t2 " +
                        "WHERE t2.id = t1.id AND t2.tag_id = %d) " +
                        "ORDER BY update_time DESC " +
                        "LIMIT 5 OFFSET %d) AS s", tagId, (page - 1) * 15))
                .JOIN("story_tag st ON s.id = st.story_id")
                .JOIN("tag t ON t.id = st.tag_id")
                .ORDER_BY(String.format("%s DESC, update_time DESC, tag_id ASC", sort));
        if (tagId == -1) return sql1.toString();
        return sql2.toString();

    }

    public String storyNum(@io.lettuce.core.dynamic.annotation.Param("tagId") Long tagId) {
        SQL sql1 = new SQL()
                .SELECT("COUNT(1)")
                .FROM("story");

        SQL sql2 = new SQL()
                .SELECT("COUNT(1)")
                .FROM(String.format("(SELECT * FROM story t1 WHERE EXISTS (" +
                        "SELECT * FROM (" +
                        "SELECT s.id, t.id AS tag_id " +
                        "FROM story s " +
                        "JOIN story_tag st ON s.id = st.story_id " +
                        "JOIN tag t ON t.id = st.tag_id) t2 " +
                        "WHERE t2.id = t1.id AND t2.tag_id = %d) ", tagId))
                .JOIN("story_tag st ON s.id = st.story_id")
                .JOIN("tag t ON t.id = st.tag_id");
        if (tagId == -1) return sql1.toString();
        return sql2.toString();

    }
}
