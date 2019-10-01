package com.sctt.cinema.api.business.controller.rest;

import com.sctt.cinema.api.business.entity.jpa.BuzConfig;
import com.sctt.cinema.api.business.service.jpa.BuzConfigService;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log4j2
public class BuzConfigRestController {

    @Autowired
    private BuzConfigService service;

    @GetMapping("/buzconfigs")
    public BaseResponse findAll(){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = service.findAll();
        } catch (Exception e){
            log.error("[findAll] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @GetMapping("/buzconfigs/{section}/{buzKey}")
    public BaseResponse findByID(@PathVariable String section, @PathVariable String buzKey){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            res.data = service.findById(String.format("%s_%s", section, buzKey));
            if (res.data == null)
                res = new BaseResponse(ReturnCodeEnum.BUZ_CONFIG_NOT_FOUND);
        } catch (Exception e){
            log.error("[findByID] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PostMapping("/buzconfigs")
    public BaseResponse insert(@RequestBody BuzConfig entity){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            if (!entity.isValid()){
                return new BaseResponse(ReturnCodeEnum.DATA_NOT_VALID);
            }
            res.data = service.create(entity);
        } catch (Exception e){
            log.error("[insert] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @PutMapping("/buzconfigs/{section}/{buzKey}")
    public BaseResponse update(@PathVariable String section, @PathVariable String buzKey, @RequestBody BuzConfig entity){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            entity.section = section;
            entity.buzKey = buzKey;
            if (!entity.isValid()){
                return new BaseResponse(ReturnCodeEnum.DATA_NOT_VALID);
            }
            res.data = service.update(entity);
        } catch (Exception e){
            log.error("[update] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    @DeleteMapping("/buzconfigs/{section}/{buzKey}")
    public BaseResponse delete(@PathVariable String section, @PathVariable String buzKey){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            service.delete(String.format("%s_%s", section, buzKey));
            res.data = true;
        } catch (Exception e){
            log.error("[delete] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }
}