package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: 陈建春
 * @Date: 2018-12-15 14:14
 * @Description: 分页结果的类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Long total;
    private List<T> rows;
}
