package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<ProductDetailVo> detailRESTful(@PathVariable Integer productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false)String keyword,
                                         @RequestParam(value = "categoryId",required = false)Integer categoryId,
                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }

    //http://www.617076674.cn/product/%E6%89%8B%E6%9C%BA/100012/1/10/price_asc
    @RequestMapping(value = "/{keyword}/{categoryId}/{pageNum}/{pageSize}/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listRESTful(@PathVariable(value = "keyword")String keyword,
                                         @PathVariable(value = "categoryId")Integer categoryId,
                                         @PathVariable(value = "pageNum") Integer pageNum,
                                         @PathVariable(value = "pageSize") Integer pageSize,
                                         @PathVariable(value = "orderBy") String orderBy){
        if (null == pageNum) {
            pageNum = 1;
        }
        if (null == pageSize) {
            pageNum = 10;
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }

    //http://www.617076674.cn/product/100012/1/10/price_asc
    //Controller并不能识别100012是String类型还是Integer类型，因此会报如下错误
    //Ambiguous handler methods mapped for HTTP path 'http://www.617076674.cn/product/100012/1/10/price_asc':
    //{public com.mmall.common.ServerResponse com.mmall.controller.portal.ProductController.listRESTfulBadcase
    // (java.lang.String,java.lang.Integer,java.lang.Integer,java.l
    @RequestMapping(value = "/{categoryId}/{pageNum}/{pageSize}/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listRESTfulBadcase(@PathVariable(value = "categoryId")Integer categoryId,
                                                @PathVariable(value = "pageNum") Integer pageNum,
                                                @PathVariable(value = "pageSize") Integer pageSize,
                                                @PathVariable(value = "orderBy") String orderBy){
        if (null == pageNum) {
            pageNum = 1;
        }
        if (null == pageSize) {
            pageNum = 10;
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory("", categoryId, pageNum, pageSize, orderBy);
    }

    @RequestMapping(value = "/{keyword}/{pageNum}/{pageSize}/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listRESTfulBadcase(@PathVariable(value = "keyword")String keyword,
                                                @PathVariable(value = "pageNum") Integer pageNum,
                                                @PathVariable(value = "pageSize") Integer pageSize,
                                                @PathVariable(value = "orderBy") String orderBy){
        if (null == pageNum) {
            pageNum = 1;
        }
        if (null == pageSize) {
            pageNum = 10;
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory(keyword, null, pageNum, pageSize, orderBy);
    }

    //http://www.617076674.cn/product/category/100012/1/10/price_asc
    //自定义一个资源占位，区分模棱两可的方法
    @RequestMapping(value = "/category/{categoryId}/{pageNum}/{pageSize}/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listRESTful(@PathVariable(value = "categoryId")Integer categoryId,
                                                       @PathVariable(value = "pageNum") Integer pageNum,
                                                       @PathVariable(value = "pageSize") Integer pageSize,
                                                       @PathVariable(value = "orderBy") String orderBy){
        if (null == pageNum) {
            pageNum = 1;
        }
        if (null == pageSize) {
            pageNum = 10;
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory("", categoryId, pageNum, pageSize, orderBy);
    }

    //http://www.617076674.cn/product/keyword/%E6%89%8B%E6%9C%BA/1/10/price_asc
    @RequestMapping(value = "/keyword/{keyword}/{pageNum}/{pageSize}/{orderBy}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listRESTful(@PathVariable(value = "keyword")String keyword,
                                                       @PathVariable(value = "pageNum") Integer pageNum,
                                                       @PathVariable(value = "pageSize") Integer pageSize,
                                                       @PathVariable(value = "orderBy") String orderBy){
        if (null == pageNum) {
            pageNum = 1;
        }
        if (null == pageSize) {
            pageNum = 10;
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory(keyword, null, pageNum, pageSize, orderBy);
    }

}
