package com.backend.passthemon.repository;
import com.backend.passthemon.entity.Authority;

import java.util.List;
public interface AuthorityRepository {
    void addAuthority(List<Authority> authorities);
}
