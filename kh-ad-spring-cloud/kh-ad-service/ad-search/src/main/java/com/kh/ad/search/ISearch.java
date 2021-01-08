package com.kh.ad.search;

import com.kh.ad.search.vo.SearchRequest;
import com.kh.ad.search.vo.SearchResponse;

/**
 * @author han.ke
 */
public interface ISearch {
    /**
     * 根据request返回response
     *
     * @param request
     * @return
     */
    SearchResponse fetchAds(SearchRequest request);
}
