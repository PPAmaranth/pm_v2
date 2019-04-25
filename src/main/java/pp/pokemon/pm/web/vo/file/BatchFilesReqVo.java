package pp.pokemon.pm.web.vo.file;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class BatchFilesReqVo {

    @Size(max = 1000, message = "最多删除1000个文件")
    private List<Integer> list;
}

