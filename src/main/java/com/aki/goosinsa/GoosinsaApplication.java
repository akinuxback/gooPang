package com.aki.goosinsa;

import com.aki.goosinsa.domain.domain.Address;
import com.aki.goosinsa.domain.domain.AddressDto;
import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.uploadFile.FileType;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.company.CompanyStatus;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.item.UploadFile;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.domain.entity.user.UserDto;
import com.aki.goosinsa.domain.entity.user.UserRole;
import com.aki.goosinsa.repository.company.CompanyRepository;
import com.aki.goosinsa.repository.item.ItemRepository;
import com.aki.goosinsa.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.stream.IntStream;

@SpringBootApplication
@RequiredArgsConstructor
public class GoosinsaApplication extends SpringBootServletInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final CompanyRepository companyRepository;
	private final ItemRepository itemRepository;
	private final EntityManager em;

	private String UPLOAD_FOLDER = System.getProperty("user.home") + File.separator + "upload" + File.separator;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(GoosinsaApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(GoosinsaApplication.class, args);
	}

	public UploadFileDto makeUploadFileDto(String pathStr){
		String pathReplace = pathStr.replaceAll("/", Matcher.quoteReplacement(File.separator));

		return UploadFileDto.builder()
				.clientFileName("test.jpg")
				.extFileName("jpg")
				.fileType(FileType.IMAGE)
				.fullPath(pathReplace)
				.serverFileName("test.jpg")
				.build();
	}

	@Override
	public void run(String... args) throws Exception {

	}

	//	@Override
//	public void run(String... args) throws Exception {
//		Address address = new Address("????????? ????????? ?????????", "123-4 ?????? 3004???", "999-999");
//		AddressDto addressDto = new AddressDto("????????? ????????? ?????????", "102-5 601???", "111-111");
//
//		/**
//		 * ?????? ??????
//		 * */
//		UserDto userDto = UserDto.builder()
//				.role(UserRole.ROLE_ADMIN)
//				.username("aki01")
//				.name("?????????")
//				.password("1111")
//				.email("akinux@gmail.com")
//				.phoneNumber("010-6455-9777")
//				.addressDto(addressDto)
//				.build();
//
//		User user = User.toEntity(userDto, bCryptPasswordEncoder);
//		userRepository.save(user);
//
//		User findUser1 = userRepository.findById(1L).get();
//
//		/**
//		 * ?????? ????????? ?????? ??????
//		 * */
//		String companyShark = "src/main/resources/static/my-custom/images/company/shark.jpg";
//		UploadFileDto uploadFileDtoCompany1 = makeUploadFileDto(companyShark);
//
//		String companyByungangse = "src/main/resources/static/my-custom/images/company/byungangse.jpg";
//		UploadFileDto uploadFileDtoCompany2 = makeUploadFileDto(companyByungangse);
//
//		String companyStarbugs = "src/main/resources/static/my-custom/images/company/starbugs.jpg";
//		UploadFileDto uploadFileDtoCompany3 = makeUploadFileDto(companyStarbugs);
//
//		String companyJuicy = "src/main/resources/static/my-custom/images/company/juicy.jpg";
//		UploadFileDto uploadFileDtoCompany4 = makeUploadFileDto(companyJuicy);
//
//		String companyKorean1 = "src/main/resources/static/my-custom/images/company/koreanFood1.jpg";
//		UploadFileDto uploadFileDtoCompany5 = makeUploadFileDto(companyKorean1);
//
//		String companyKorean2 = "src/main/resources/static/my-custom/images/company/koreanFood2.jpg";
//		UploadFileDto uploadFileDtoCompany6 = makeUploadFileDto(companyKorean2);
//
//		String companyKyochon = "src/main/resources/static/my-custom/images/company/kyochon.jpg";
//		UploadFileDto uploadFileDtoCompany7 = makeUploadFileDto(companyKyochon);
//
//		String companyMexican = "src/main/resources/static/my-custom/images/company/mexican.jpg";
//		UploadFileDto uploadFileDtoCompany8 = makeUploadFileDto(companyMexican);
//
//		/**
//		 * ?????? ??????????????? ??????
//		 * */
//		String ddukbok = "src/main/resources/static/my-custom/images/food/ddukbok.jpg";
//		UploadFileDto uploadFileDtoFood1 = makeUploadFileDto(ddukbok);
//
//		String soonDae = "src/main/resources/static/my-custom/images/food/soondae.jpg";
//		UploadFileDto uploadFileDtoFood2 = makeUploadFileDto(soonDae);
//
//		String coffee1 = "src/main/resources/static/my-custom/images/food/coffee1.jpg";
//		UploadFileDto uploadFileDtoFood5 = makeUploadFileDto(coffee1);
//
//		String coffee2 = "src/main/resources/static/my-custom/images/food/coffee2.jpg";
//		UploadFileDto uploadFileDtoFood6 = makeUploadFileDto(coffee2);
//
//		String juice1 = "src/main/resources/static/my-custom/images/food/juice1.jpg";
//		UploadFileDto uploadFileDtoFood7 = makeUploadFileDto(juice1);
//
//		String juice2 = "src/main/resources/static/my-custom/images/food/juice2.jpg";
//		UploadFileDto uploadFileDtoFood8 = makeUploadFileDto(juice2);
//
//		String kimchi = "src/main/resources/static/my-custom/images/food/kimchi.jpg";
//		UploadFileDto uploadFileDtoFood9 = makeUploadFileDto(kimchi);
//
//		String budae = "src/main/resources/static/my-custom/images/food/budae.jpg";
//		UploadFileDto uploadFileDtoFood10 = makeUploadFileDto(budae);
//
//		String samgaeTang = "src/main/resources/static/my-custom/images/food/samgaeTang.jpg";
//		UploadFileDto uploadFileDtoFood11 = makeUploadFileDto(samgaeTang);
//
//		String galbiTang = "src/main/resources/static/my-custom/images/food/galbiTang.jpg";
//		UploadFileDto uploadFileDtoFood12 = makeUploadFileDto(galbiTang);
//
//		String chicken1 = "src/main/resources/static/my-custom/images/food/chicken1.jpg";
//		UploadFileDto uploadFileDtoFood13 = makeUploadFileDto(chicken1);
//
//		String chicken2 = "src/main/resources/static/my-custom/images/food/chicken2.jpg";
//		UploadFileDto uploadFileDtoFood14 = makeUploadFileDto(chicken2);
//
//		String yang = "src/main/resources/static/my-custom/images/food/yang.jpg";
//		UploadFileDto uploadFileDtoFood15 = makeUploadFileDto(yang);
//
//		String fried = "src/main/resources/static/my-custom/images/food/fried.jpg";
//		UploadFileDto uploadFileDtoFood16 = makeUploadFileDto(fried);
//
//		IntStream.rangeClosed(1,5).forEach(i -> {
//			Company company1 = Company.builder()
//					.companyNo("111-1111-1" + i)
//					.companyName("???????????????" + i)
//					.abbr("??????")
//					.foodGroups(FoodGroups.SCHOOLFOOD)
//					.foodGroupsOfTitle("?????????")
//					.status(CompanyStatus.Food)
//					.address(address)
//					.uploadFile(UploadFile.createUploadFile(uploadFileDtoCompany1))
//					.user(findUser1)
//					.build();
//
//			companyRepository.save(company1);
//
//			Company company2 = Company.builder()
//					.companyNo("222-2222-2" + i)
//					.companyName("???????????????" + i)
//					.abbr("?????????")
//					.foodGroups(FoodGroups.SCHOOLFOOD)
//					.foodGroupsOfTitle("?????????")
//					.status(CompanyStatus.Food)
//					.address(address)
//					.uploadFile(UploadFile.createUploadFile(uploadFileDtoCompany2))
//					.user(findUser1)
//					.build();
//
//			companyRepository.save(company2);
//
//			Company company3 = Company.builder()
//					.companyNo("333-3333-3" + i)
//					.companyName("????????????" + i)
//					.abbr("??????")
//					.foodGroups(FoodGroups.DRINK)
//					.foodGroupsOfTitle("??????")
//					.status(CompanyStatus.Food)
//					.address(address)
//					.uploadFile(UploadFile.createUploadFile(uploadFileDtoCompany3))
//					.user(findUser1)
//					.build();
//
//			companyRepository.save(company3);
//
//			Company company4 = Company.builder()
//					.companyNo("444-4444-4" + i)
//					.companyName("??????" + i)
//					.abbr("??????")
//					.foodGroups(FoodGroups.DRINK)
//					.foodGroupsOfTitle("??????")
//					.status(CompanyStatus.Food)
//					.address(address)
//					.uploadFile(UploadFile.createUploadFile(uploadFileDtoCompany4))
//					.user(findUser1)
//					.build();
//
//			companyRepository.save(company4);
//
//			Company company5 = Company.builder()
//					.companyNo("555-5555-5" + i)
//					.companyName("????????????" + i)
//					.abbr("??????")
//					.foodGroups(FoodGroups.SOUP)
//					.foodGroupsOfTitle("???")
//					.status(CompanyStatus.Food)
//					.address(address)
//					.uploadFile(UploadFile.createUploadFile(uploadFileDtoCompany5))
//					.user(findUser1)
//					.build();
//
//			companyRepository.save(company5);
//
//			Company company6 = Company.builder()
//					.companyNo("666-6666-6" + i)
//					.companyName("????????????" + i)
//					.abbr("?????????")
//					.foodGroups(FoodGroups.SOUP)
//					.foodGroupsOfTitle("??????")
//					.status(CompanyStatus.Food)
//					.address(address)
//					.uploadFile(UploadFile.createUploadFile(uploadFileDtoCompany6))
//					.user(findUser1)
//					.build();
//
//			companyRepository.save(company6);
//
//			Company company7 = Company.builder()
//					.companyNo("777-7777-7" + i)
//					.companyName("????????????" + i)
//					.abbr("??????")
//					.foodGroups(FoodGroups.CHICKEN)
//					.foodGroupsOfTitle("??????")
//					.status(CompanyStatus.Food)
//					.address(address)
//					.uploadFile(UploadFile.createUploadFile(uploadFileDtoCompany7))
//					.user(findUser1)
//					.build();
//
//			companyRepository.save(company7);
//
//			Company company8 = Company.builder()
//					.companyNo("888-8888-8" + i)
//					.companyName("???????????????" + i)
//					.abbr("?????????")
//					.foodGroups(FoodGroups.CHICKEN)
//					.foodGroupsOfTitle("???")
//					.status(CompanyStatus.Food)
//					.address(address)
//					.uploadFile(UploadFile.createUploadFile(uploadFileDtoCompany8))
//					.user(findUser1)
//					.build();
//
//			companyRepository.save(company8);
//
//
//			IntStream.rangeClosed(1, 5).forEach(j ->{
//				FoodItemDto foodItemDto1 = new FoodItemDto("??????????????????", i * 5000, j * 10, "???????????? ????????????", uploadFileDtoFood1, company1,
//						FoodGroups.SCHOOLFOOD, "?????????");
//
//				FoodItemDto foodItemDto2 = new FoodItemDto("????????????", i * 10000, j * 10, "???????????? ????????????", uploadFileDtoFood2, company1,
//						FoodGroups.SCHOOLFOOD, "??????");
//
//
//				FoodItem foodItem1 = new FoodItem(foodItemDto1);
//				itemRepository.save(foodItem1);
//
//				FoodItem foodItem2 = new FoodItem(foodItemDto2);
//				itemRepository.save(foodItem2);
//
//				FoodItemDto foodItemDto3 = new FoodItemDto("??????????????????", i * 5000, j * 10, "???????????? ????????????", uploadFileDtoFood1, company2,
//						FoodGroups.SCHOOLFOOD, "?????????");
//
//				FoodItemDto foodItemDto4 = new FoodItemDto("????????????", i * 10000, j * 10, "???????????? ????????????", uploadFileDtoFood2, company2,
//						FoodGroups.SCHOOLFOOD, "??????");
//
//
//				FoodItem foodItem3 = new FoodItem(foodItemDto3);
//				itemRepository.save(foodItem3);
//
//				FoodItem foodItem4 = new FoodItem(foodItemDto4);
//				itemRepository.save(foodItem4);
//
//				FoodItemDto foodItemDto5 = new FoodItemDto("??????1", i * 5000, j * 10, "???????????? ????????????", uploadFileDtoFood5, company3,
//						FoodGroups.DRINK, "??????");
//
//				FoodItemDto foodItemDto6 = new FoodItemDto("??????1", i * 10000, j * 10, "???????????? ????????????", uploadFileDtoFood6, company3,
//						FoodGroups.DRINK, "??????");
//
//
//				FoodItem foodItem5 = new FoodItem(foodItemDto5);
//				itemRepository.save(foodItem5);
//
//				FoodItem foodItem6 = new FoodItem(foodItemDto6);
//				itemRepository.save(foodItem6);
//
//				FoodItemDto foodItemDto7 = new FoodItemDto("????????????", i * 5000, j * 10, "???????????? ????????????", uploadFileDtoFood7, company4,
//						FoodGroups.DRINK, "??????");
//
//				FoodItemDto foodItemDto8 = new FoodItemDto("???????????????", i * 10000, j * 10, "???????????? ????????????", uploadFileDtoFood8, company4,
//						FoodGroups.DRINK, "??????");
//
//
//				FoodItem foodItem7 = new FoodItem(foodItemDto7);
//				itemRepository.save(foodItem7);
//
//				FoodItem foodItem8 = new FoodItem(foodItemDto8);
//				itemRepository.save(foodItem8);
//
//
//
//				/**
//				 * */
//				FoodItemDto foodItemDto9 = new FoodItemDto("????????? ????????????", i * 5000, j * 10, "???????????? ????????????", uploadFileDtoFood9, company5,
//						FoodGroups.SOUP, "???");
//
//				FoodItemDto foodItemDto10 = new FoodItemDto("BTS ARMY ????????????", i * 10000, j * 10, "???????????? ????????????", uploadFileDtoFood10, company5,
//						FoodGroups.SOUP, "???");
//
//
//				FoodItem foodItem9 = new FoodItem(foodItemDto9);
//				itemRepository.save(foodItem9);
//
//				FoodItem foodItem10 = new FoodItem(foodItemDto10);
//				itemRepository.save(foodItem10);
//
//
//				FoodItemDto foodItemDto11 = new FoodItemDto("?????????????????????", i * 5000, j * 10, "???????????? ????????????", uploadFileDtoFood11, company6,
//						FoodGroups.SOUP, "??????");
//
//				FoodItemDto foodItemDto12 = new FoodItemDto("??????????????????", i * 10000, j * 10, "???????????? ????????????", uploadFileDtoFood12, company6,
//						FoodGroups.SOUP, "??????");
//
//
//				FoodItem foodItem11 = new FoodItem(foodItemDto11);
//				itemRepository.save(foodItem11);
//
//				FoodItem foodItem12 = new FoodItem(foodItemDto12);
//				itemRepository.save(foodItem12);
//
//
//				/**
//				 **/
//				FoodItemDto foodItemDto13 = new FoodItemDto("??????????????????", i * 5000, j * 10, "???????????? ????????????", uploadFileDtoFood13, company7,
//						FoodGroups.CHICKEN, "??????");
//
//				FoodItemDto foodItemDto14 = new FoodItemDto("????????????", i * 10000, j * 10, "???????????? ????????????", uploadFileDtoFood14, company7,
//						FoodGroups.CHICKEN, "???");
//
//
//				FoodItem foodItem13 = new FoodItem(foodItemDto13);
//				itemRepository.save(foodItem13);
//
//				FoodItem foodItem14 = new FoodItem(foodItemDto14);
//				itemRepository.save(foodItem14);
//
//
//				FoodItemDto foodItemDto15 = new FoodItemDto("???????????????", i * 5000, j * 10, "???????????? ????????????", uploadFileDtoFood15, company8,
//						FoodGroups.CHICKEN, "??????");
//
//				FoodItemDto foodItemDto16 = new FoodItemDto("?????????????????????", i * 10000, j * 10, "???????????? ????????????", uploadFileDtoFood16, company8,
//						FoodGroups.CHICKEN, "???");
//
//
//				FoodItem foodItem15 = new FoodItem(foodItemDto15);
//				itemRepository.save(foodItem15);
//
//				FoodItem foodItem16 = new FoodItem(foodItemDto16);
//				itemRepository.save(foodItem16);
//
//			});
//		});
//
//	}


//	@Override
//	public void run(String... args) throws Exception {
//
//		Address address = new Address("????????? ????????? ?????????", "123-4 ?????? 3004???", "999-999");
//
//		UserDto userDto = UserDto.builder()
//				.role(UserRole.ROLE_ADMIN)
//				.username("aki01")
//				.name("?????????")
//				.password("1111")
//				.email("akinux@gmail.com")
//				.phoneNumber("010-6455-9777")
//				.addressDto(new AddressDto("????????? ????????? ?????????", "102-5 601???", "111-111"))
//				.build();
//
//		UploadFileDto uploadFile1 = UploadFileDto.builder()
//				.clientFileName("?????????10.jpg")
//				.extFileName("jpg")
//				.fileType(FileType.IMAGE)
//				.fullPath("C:\\Users\\icino\\upload\\2021\\08\\24\\d05d06f4-9738-46bc-bf69-42ba3fdc3850.jpg")
//				.serverFileName("d05d06f4-9738-46bc-bf69-42ba3fdc3850.jpg")
//				.uploadFolder("C:\\Users\\icino\\upload\\")
//				.uploadPath("2021\\08\\24")
//				.build();
//
//		UploadFileDto uploadFile2 = UploadFileDto.builder()
//				.clientFileName("?????????10.jpg")
//				.extFileName("jpg")
//				.fileType(FileType.IMAGE)
//				.fullPath("C:\\Users\\icino\\upload\\2021\\08\\24\\d05d06f4-9738-46bc-bf69-42ba3fdc3850.jpg")
//				.serverFileName("d05d06f4-9738-46bc-bf69-42ba3fdc3850.jpg")
//				.uploadFolder("C:\\Users\\icino\\upload\\")
//				.uploadPath("2021\\08\\24")
//				.build();
//
//		User user = User.toEntity(userDto, bCryptPasswordEncoder);
//		userRepository.save(user);
//
//		User findUser1 = userRepository.findById(1L).get();
//
//
//		IntStream.rangeClosed(1, 10).forEach(i -> {
//			Company company1 = Company.builder()
//					.companyNo("111-1111-1"+ i)
//					.companyName("??????" + i)
//					.abbr("??????")
//					.foodGroups(FoodGroups.DRINK)
//					.foodGroupsOfTitle("??????")
//					.status(CompanyStatus.Food)
//					.address(address)
//					.uploadFile(UploadFile.createUploadFile(uploadFile1))
//					.user(findUser1)
//					.build();
//
//			companyRepository.save(company1);
//
//		});
//
//		IntStream.rangeClosed(1, 10).forEach(i -> {
//			Company company2 = Company.builder()
//					.companyNo("333-3333-3"+ i)
//					.companyName("???????????????" + i)
//					.abbr("?????????")
//					.foodGroups(FoodGroups.CHICKEN)
//					.foodGroupsOfTitle("??????")
//					.status(CompanyStatus.Food)
//					.address(address)
//					.uploadFile(UploadFile.createUploadFile(uploadFile2))
//					.user(findUser1)
//					.build();
//
//			companyRepository.save(company2);
//		});
//
//		IntStream.rangeClosed(1, 10).forEach(i -> {
//			Company company3 = Company.builder()
//					.companyNo("222-2222-2"+ i)
//					.companyName("????????????" + i)
//					.abbr("??????")
//					.foodGroups(FoodGroups.SOUP)
//					.foodGroupsOfTitle("???")
//					.status(CompanyStatus.Food)
//					.address(address)
//					.uploadFile(UploadFile.createUploadFile(uploadFile2))
//					.user(findUser1)
//					.build();
//
//			companyRepository.save(company3);
//		});
//
//
//		Company company1 = companyRepository.findById("111-1111-11").get();
//		Company company2 = companyRepository.findById("222-2222-21").get();
//
//		FoodItemDto foodItemDto1 = new FoodItemDto("?????????", 10000, 30, "???????????? ????????????", uploadFile1, company1,
//				FoodGroups.CHICKEN, "??????");
//
//		FoodItemDto foodItemDto2 = new FoodItemDto("?????????", 200000, 10, "???????????? ????????????", uploadFile2, company2,
//				FoodGroups.DRINK, "??????");
//
//
//
//		IntStream.rangeClosed(1, 10).forEach(i ->{
//			FoodItem foodItem1 = new FoodItem(foodItemDto1);
//			itemRepository.save(foodItem1);
//
//		});
//
//		IntStream.rangeClosed(1, 10).forEach(i ->{
//			FoodItem foodItem2 = new FoodItem(foodItemDto2);
//			itemRepository.save(foodItem2);
//		});
//
//	}

}
