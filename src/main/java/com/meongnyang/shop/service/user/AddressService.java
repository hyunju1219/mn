package com.meongnyang.shop.service.user;

import com.meongnyang.shop.entity.Address;
import com.meongnyang.shop.repository.user.UserAddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressService {
    private final UserAddressMapper addressMapper;

    public Boolean isExistAddress(Long userId) {
        Address address = addressMapper.findAddressByUserId(userId);
        return address != null;
    }

    public void registerAddress(Address address) {
        addressMapper.saveAddress(address);
    }

    public void updateAddress(Address address) {
        addressMapper.UpdateAddressByUserId(address);
    }
}
