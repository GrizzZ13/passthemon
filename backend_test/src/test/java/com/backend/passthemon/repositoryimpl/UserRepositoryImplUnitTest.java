package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.AuthorityDao;
import com.backend.passthemon.dao.FollowDao;
import com.backend.passthemon.dao.UserDao;
import com.backend.passthemon.entity.Authority;
import com.backend.passthemon.entity.Follow;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.redis.RedisService;
import com.backend.passthemon.utils.keyutils.KeyUtil;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class UserRepositoryImplUnitTest {
    @Mock
    UserDao userDao;
    @Mock
    FollowDao followDao;
    @Mock
    AuthorityDao authorityDao;
    @Mock
    RedisService redisService;
    @InjectMocks
    UserRepositoryImpl userRepository;

    private Integer userId = 1;

    private String email = "391678792hd@sjtu.edu.cn", username = "username", authorityName = "authorityName";

    private User user = new User(userId);

    private List<Follow> followList = new ArrayList<>();

    private List<Authority> authorityList = new ArrayList<>();

    private Authority authority = new Authority();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(userDao.findUserById(userId)).thenReturn(user);
        Mockito.when(userDao.getUserByEmail(email)).thenReturn(user);
        Mockito.when(userDao.save(user)).thenReturn(user);
        Mockito.when(followDao.findAllByUser(user)).thenReturn(followList);
        Mockito.when(authorityDao.findAllByUser(user)).thenReturn(authorityList);
    }
    @Test
    public void findUserById() {
        Assert.assertEquals(userRepository.findUserById(userId), user);
        Mockito.verify(userDao).findUserById(userId);
    }
    @Test
    public void getUserByEmail() {
        Assert.assertEquals(userRepository.getUserByEmail(email), user);
        Mockito.verify(userDao).getUserByEmail(email);
    }
    @Test
    public void save() {
        Assert.assertEquals(userRepository.save(user), user);
        Mockito.verify(userDao).save(user);
        Mockito.verify(redisService).set(KeyUtil.UserIdKey(user.getId()), user);
    }
    @Test
    public void getFollowsByUserid() {
        Assert.assertEquals(userRepository.getFollowsByUserid(userId), followList);
        Mockito.verify(followDao).findAllByUser(user);
    }
    @Test
    public void listFollowByPage() {
        int fetchPage = 1;
        PageRequest pageRequest = PageRequest.of(fetchPage,8);
        Page<Follow> page = new Page<Follow>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Follow, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Follow> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Follow> iterator() {
                return null;
            }
        };
        Mockito.when(followDao.findAllByUser(user, pageRequest)).thenReturn(page);
        Assert.assertNull(userRepository.listFollowsByPage(fetchPage, userId));
        Mockito.verify(followDao).findAllByUser(user, pageRequest);
    }
    @Test
    public void getUserNameById() {
        user.setUsername(username);
        Assert.assertEquals(userRepository.getUserNameById(userId), username);
        Mockito.verify(userDao).findUserById(userId);
    }
    @Test
    public void getAuthorityByUserid() {
        authority.setAuthorityName(authorityName);
        authorityList.add(authority);
        List<String> result = new ArrayList<>();
        result.add(authorityName);
        Assert.assertEquals(userRepository.getAuthorityByUserid(userId), result);
        Mockito.verify(authorityDao).findAllByUser(user);
    }
}
