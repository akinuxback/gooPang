package com.aki.goosinsa.repository.company;

import com.aki.goosinsa.domain.dto.company.CompanyDto;
import com.aki.goosinsa.domain.dto.company.CompanySearch;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QDCompanyRepository {

    Page<Company> findAllPaging(Pageable pageable, CompanySearch companySearch);


    /**
     * manager 의 companyList page
     * - 로그인한 유저로 부터 id 값을 받아, 로그인한 해당 유저의 보유 company 들의 uploadFile 과 user 정보를 가져온다.
     * */
    public List<Company> companyOfUserAndUploadFileJoin(Long userId);

    /**
    *  companyNo로 조회한 company 의 검색조건 해당하는 아이템리스트 가져오기
    * */
    public Page<FoodItem> companyFindFoodItemList(String companyNo, CompanySearch companySearch);

    public CompanyDto companyJoinUserAndUploadFileFindByCompanyNo(String companyNo);

}
