package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.FollowDao;
import com.backend.passthemon.entity.Follow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FollowRepositoryImplUnitTest {
    @Mock
    FollowDao followDao;
    @InjectMocks
    private FollowRepositoryImpl followRepository;

    private Follow follow=new Follow();

    private Integer followId = 1;

    @Before
    public void setUp() throws Exception{
        follow.setId(1);
        MockitoAnnotations.openMocks(this);
        Mockito.when(followDao.saveAndFlush(follow)).thenReturn(follow);
    }
    @Test
    public void addFollow() {
        Follow result=followRepository.addFollow(follow);
        Assert.assertEquals(result,follow);
        Mockito.verify(followDao).saveAndFlush(follow);
    }

    @Test
    public void unFollow() {
        Mockito.when(followDao.getOne(followId)).thenReturn(follow);
        followRepository.unFollow(followId);
        Mockito.verify(followDao).delete(follow);
    }

}
