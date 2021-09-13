package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.config.OffsetBasedPageRequest;
import com.backend.passthemon.dao.ChatDao;
import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.Chat;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class ChatRepositoryImplUnitTest {
    @Mock
    ChatDao chatDao;
    @InjectMocks
    ChatRepositoryImpl chatRepository;

    private Chat chat = new Chat();

    private Iterable<Chat> entities = null;

    private Integer pageNumber = 1, counts = 1;

    private Channel channel1 = new Channel(), channel2 = new Channel();

    private Page<Chat> chatPage = new Page<Chat>() {
        @Override
        public int getTotalPages() {
            return 0;
        }

        @Override
        public long getTotalElements() {
            return 0;
        }

        @Override
        public <U> Page<U> map(Function<? super Chat, ? extends U> converter) {
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
        public List<Chat> getContent() {
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
        public Iterator<Chat> iterator() {
            return null;
        }
    };

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Mockito.when(chatDao.saveAll(entities)).thenReturn(null);
    }
    @Test
    public void save() {
        chatRepository.save(chat);
        Mockito.verify(chatDao).save(chat);
    }
    @Test
    public void saveAll() {
        Assert.assertNull(chatRepository.saveAll(entities));
        Mockito.verify(chatDao).saveAll(entities);
    }
    @Test
    public void getChatHistoryByChannelsAndOffset() {
        Integer limit = 20;
        Integer offset = limit * pageNumber + counts;
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        OffsetBasedPageRequest pageRequest = new OffsetBasedPageRequest(offset, limit, sort);

        Mockito.when(chatDao.findAllByChannelEqualsOrChannelEquals(channel1, channel2, pageRequest)).thenReturn(chatPage);
        Assert.assertNull(chatRepository.getChatHistoryByChannelsAndOffset(channel1, channel2, counts, pageNumber));
        Mockito.verify(chatDao).findAllByChannelEqualsOrChannelEquals(channel1,channel2, pageRequest);
    }
}
