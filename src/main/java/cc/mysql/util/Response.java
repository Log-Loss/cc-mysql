package cc.mysql.util;

import java.util.List;

public class Response {
    public Response(int code, String message, Object result, boolean paging) {
        this(code, message, result, paging, null, null);
    }

    public Response(int code, String message, Object result, Boolean paging, Integer page, Integer pageSize) {
        this.code = code;
        this.message = message;
        this.paging = paging;
        if (paging) {
            if (page == null) {
                page = 0;
            }
            if (pageSize == null) {
                pageSize = 20;
            }
            List<Object> tmp = ((List<Object>) result);
            this.page = page;
            this.pageSize = pageSize;
            size = tmp.size();
            if (page * pageSize >= size) {
                this.message = "error: page over";
                this.code = 404;
                this.result = null;
                return;
            }
            if (page * pageSize < size && (page + 1) * pageSize >= size) {
                this.message = "waring: page truncate";
                this.result = tmp.subList(page * pageSize, size);
                return;
            }
            this.result = tmp.subList(page * pageSize, (page + 1) * pageSize);
        } else {
            this.page = -1;
            this.pageSize = -1;
            this.size = -1;
            this.result = result;
        }
    }

    public int code;
    public String message;
    public int page;
    public int pageSize;
    public int size;
    public Object result;
    public boolean paging;
}
