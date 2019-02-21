package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(Product product) {
        //全部通过拦截器验证是否登录以及权限
        return iProductService.saveOrUpdateProduct(product);
    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        //全部通过拦截器验证是否登录以及权限
        return iProductService.setSaleStatus(productId,status);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(Integer productId) {
        //全部通过拦截器验证是否登录以及权限
        return iProductService.manageProductDetail(productId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        //全部通过拦截器验证是否登录以及权限
        return iProductService.getProductList(pageNum, pageSize);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(String productName, Integer productId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        //全部通过拦截器验证是否登录以及权限
        return iProductService.searchProduct(productName, productId, pageNum, pageSize);
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 HttpServletRequest request) {
        //全部通过拦截器验证是否登录以及权限
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);
    }

    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(@RequestParam(value = "upload_file",required = false) MultipartFile file,
                                 HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();
        //全部通过拦截器验证是否登录以及权限
        //富文本中对于返回值有自己的要求,我们使用是simditor所以按照simditor的要求进行返回
//        {
//            "success": true/false,
//                "msg": "error message", # optional
//            "file_path": "[real file path]"
//        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);
        if(StringUtils.isBlank(targetFileName)){
            resultMap.put("success",false);
            resultMap.put("msg","上传失败");
            return resultMap;
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        resultMap.put("success",true);
        resultMap.put("msg","上传成功");
        resultMap.put("file_path",url);
        response.addHeader("Access-Control-Allow-Headers","X-File-Name");
        return resultMap;
    }

}
