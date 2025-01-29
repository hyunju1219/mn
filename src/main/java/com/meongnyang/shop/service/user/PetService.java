package com.meongnyang.shop.service.user;

import com.meongnyang.shop.entity.Pet;
import com.meongnyang.shop.repository.user.UserPetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PetService {
    private final UserPetMapper petMapper;

    public Boolean isExistPet(Long userId) {
        Pet pet = petMapper.findPetByUserId(userId);
        return pet != null;
    }

    public void registerPet(Pet pet) {
        petMapper.savePet(pet);
    }

    public void updatePet(Pet pet) {
        petMapper.UpdatePetByUserId(pet);
    }
}
