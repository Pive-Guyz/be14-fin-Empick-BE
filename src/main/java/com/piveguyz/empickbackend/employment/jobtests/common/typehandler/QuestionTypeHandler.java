package com.piveguyz.empickbackend.employment.jobtests.common.typehandler;

import com.piveguyz.empickbackend.employment.jobtests.question.command.domain.aggregate.enums.QuestionType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

@MappedTypes(QuestionType.class)
public class QuestionTypeHandler extends BaseTypeHandler<QuestionType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, QuestionType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public QuestionType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return QuestionType.fromCode(rs.getInt(columnName));
    }

    @Override
    public QuestionType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return QuestionType.fromCode(rs.getInt(columnIndex));
    }

    @Override
    public QuestionType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return QuestionType.fromCode(cs.getInt(columnIndex));
    }
}
