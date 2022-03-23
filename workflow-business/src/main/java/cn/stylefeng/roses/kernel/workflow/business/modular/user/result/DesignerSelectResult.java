package cn.stylefeng.roses.kernel.workflow.business.modular.user.result;

/**
 * 用在流程设计器选人的地方
 *
 * @author fengshuonan
 * @date 2021/6/24 21:28
 */
@SuppressWarnings("all")
public class DesignerSelectResult {

    protected Integer size;
    protected Long total;
    protected Integer start;
    protected Object data;

    public DesignerSelectResult() {
    }

    public DesignerSelectResult(Object data, Integer size) {
        this.data = data;
        if (data != null) {
            size = size;
            total = (long) size;
            start = 0;
        }
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
