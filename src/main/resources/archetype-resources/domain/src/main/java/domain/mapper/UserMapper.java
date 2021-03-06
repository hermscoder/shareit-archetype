package ${package}.domain.mapper;

import ${package}.domain.UserEntity;
import ${package}.domain.dto.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toModel(UserEntity userEntity);
    UserEntity toEntity(User model);

}
