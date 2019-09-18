package com.sctt.cinema.api.business.controller.client;

import com.sctt.cinema.api.business.entity.DTO.HomeScreenDTO;
import com.sctt.cinema.api.business.entity.DTO.PromotionDTO;
import com.sctt.cinema.api.business.entity.jpa.Movie;
import com.sctt.cinema.api.business.service.jpa.MovieService;
import com.sctt.cinema.api.common.BaseResponse;
import com.sctt.cinema.api.common.enums.MovieStatusEnum;
import com.sctt.cinema.api.common.enums.ReturnCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client/public")
@Log4j2
public class ClientPublicController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/home")
    public BaseResponse homeScreen(){
        BaseResponse res = new BaseResponse(ReturnCodeEnum.SUCCESS);

        try{
            HomeScreenDTO data = new HomeScreenDTO();
            data.currentShowingMovies = getListMovies(MovieStatusEnum.CURRENT_SHOWING);
            data.comingSoonMovies = getListMovies(MovieStatusEnum.COMING_SOON);
            data.promotions = getListPromotions();

            res.data = data;
        } catch (Exception e){
            log.error("[homeScreen] ex: {}",e.getMessage());
            res = BaseResponse.EXCEPTION_RESPONSE;
        }

        return res;
    }

    private List<Movie> getListMovies(MovieStatusEnum status) {
        List<Movie> result = movieService.findAll()
                .stream()
                .filter(c -> c.status == status.getValue())
                .collect(Collectors.toList());

        return result;
    }

    private List<PromotionDTO> getListPromotions() {
        List<PromotionDTO> result = new ArrayList<>();

        return result;
    }

}
