package com.ruoyi.web.controller.fileManage;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.framework.web.domain.Server;
import com.ruoyi.system.domain.TestFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @Author：渐悟
 * @Date：2023/11/23
 * @Github: https://github.com/Twany
 * @Description：
 */
@RestController
@RequestMapping("/fileManage/fileList")
public class FileListController {
    //@GetMapping("/list/{parentId}")
    public AjaxResult getFileList(@PathVariable Integer parentId) throws Exception {

        System.out.println(parentId);

        ArrayList<TestFile> arrayList = new ArrayList<>();
        TestFile testFile = new TestFile();
        //mock一个testFile
        testFile.setId(1);
        testFile.setParentId(0);
        testFile.setFileName("testFile");
        testFile.setFileType("png");
        testFile.setDesc("测试");
        testFile.setFileSize("100kb");
        testFile.setFileUrl("http://localhost:8080/ruoyi-admin/profile/avatar/2021/01/14/1610706451286.png");

        TestFile testFile2 = new TestFile();
        //mock一个testFile
        testFile2.setId(2);
        testFile2.setParentId(0);
        testFile2.setFileName("testFile2");
        testFile2.setFileType("folder");
        testFile2.setDesc("文件夹");
        testFile2.setFileSize("");
        testFile2.setFileUrl("");

        TestFile testFile3 = new TestFile();
        testFile3.setId(3);
        testFile3.setParentId(0);
        testFile3.setFileName("testFile");
        testFile3.setFileType("mp5");
        testFile3.setDesc("测试3");
        testFile3.setFileSize("100kb");
        testFile3.setFileUrl("http://localhost:8080/ruoyi-admin/profile/avatar/2021/01/14/1610706451286.png");

        TestFile testFile4 = new TestFile();
        testFile3.setId(4);
        testFile3.setParentId(2);
        testFile3.setFileName("子文件夹测试");
        testFile3.setFileType("mp5");
        testFile3.setDesc("测试4");
        testFile3.setFileSize("100kb");
        testFile3.setFileUrl("http://localhost:8080/ruoyi-admin/profile/avatar/2021/01/14/1610706451286.png");


        arrayList.add(testFile);
        arrayList.add(testFile2);
        arrayList.add(testFile3);

        return AjaxResult.success(arrayList);
    }
}
