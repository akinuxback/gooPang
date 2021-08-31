package com.aki.goosinsa;

import com.aki.goosinsa.domain.domain.Address;
import com.aki.goosinsa.domain.domain.AddressDto;
import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.uploadFile.FileType;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.domain.entity.user.UserDto;
import com.aki.goosinsa.domain.entity.user.UserRole;
import com.aki.goosinsa.repository.company.CompanyRepository;
import com.aki.goosinsa.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;
import java.util.stream.IntStream;

@SpringBootApplication
@RequiredArgsConstructor
public class GoosinsaApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final CompanyRepository companyRepository;

	public static void main(String[] args) {
		SpringApplication.run(GoosinsaApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		UserDto userDto = UserDto.builder()
				.role(UserRole.ROLE_ADMIN)
				.username("aki01")
				.name("박보영")
				.password("1111")
				.email("akinux@gmail.com")
				.phoneNumber("010-6455-9777")
				.addressDto(new AddressDto("서울시 송파구 방이동", "102-5 601호", "111-111"))
				.build();

		User user = User.toEntity(userDto, bCryptPasswordEncoder);
		userRepository.save(user);

		IntStream.rangeClosed(1, 10).forEach(i -> {
			Company company = Company.builder()
					.companyNo("111-1111-1"+ i)
					.companyName("롯데마트" + i)
					.abbr("롯데")
					.user(user)
					.build();

			companyRepository.save(company);
		});

	}

}
