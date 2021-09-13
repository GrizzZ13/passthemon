package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Follow;
import com.backend.passthemon.entity.Verification;
import com.backend.passthemon.repository.FollowRepository;
import com.backend.passthemon.repository.VerificationRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FollowServiceImplUnitTest {
    @Mock
    FollowRepository followRepository;
    @InjectMocks
    private FollowServiceImpl followService;

    private Integer followId = 1;
    private Follow follow = new Follow();

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Mockito.when(followRepository.addFollow(follow)).thenReturn(follow);
    };
    @Test
    public void addFollow() {
        Assert.assertEquals(followService.addFollow(follow), follow);
        Mockito.verify(followRepository).addFollow(follow);
    }
    @Test
    public void unFollow() {
        followService.unFollow(followId);
        Mockito.verify(followRepository).unFollow(followId);
    }
}
