package org.slovob.slovoborg.opinion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slovob.slovoborg.definition.Definition;
import org.slovob.slovoborg.definition.DefinitionRepository;
import org.slovob.slovoborg.exception.FishyFrontendQuery;
import org.slovob.slovoborg.user.User;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class OpinionServiceTest {
    public static final String CLIENT_IP = "127.0.0.1";
    public static final long DEFINITION_ID = 1;

    private OpinionService subject;
    private OpinionTransfer opinionTransfer;
    private User user;

    @Mock
    private OpinionRepository opinionRepo;

    @Mock
    private DefinitionRepository definitionRepo;

    @BeforeEach
    public void init() {
        initMocks(this);
        subject = new OpinionService(opinionRepo, definitionRepo);
        opinionTransfer = new OpinionTransfer(DEFINITION_ID, 1); // all tests will try to like first definition
        user.setId(1);
    }

    @Test
    public void shouldFail_ifNoSuchDefinition() {
        given(definitionRepo.findById(DEFINITION_ID)).willReturn(Optional.empty());

        Assertions.assertThrows(FishyFrontendQuery.class, () -> {
            subject.processOpinion(opinionTransfer, user);
        });
    }

    @Test
    public void shouldLike_ifNoOpinionYet() {
        given(definitionRepo.findById(DEFINITION_ID)).willReturn(Optional.of(new Definition()));
        given(opinionRepo.findByDefinitionIdAndUserId(DEFINITION_ID, user.getId())).willReturn(Optional.empty());

        subject.processOpinion(opinionTransfer, user);

        Definition definition = new Definition();
        definition.like();
        verify(definitionRepo).save(definition);
        verify(opinionRepo).save(new Opinion(opinionTransfer.getOpinion(), opinionTransfer.getDefinitionId(), user));
    }

    @Test
    public void shouldUnlike_ifAlreadyLiked() {
        given(definitionRepo.findById(DEFINITION_ID)).willReturn(Optional.of(new Definition()));
        Optional<Opinion> existingOpinion = Optional.of(new Opinion(1, DEFINITION_ID, user));
        given(opinionRepo.findByDefinitionIdAndUserId(DEFINITION_ID, user.getId())).willReturn(existingOpinion);

        subject.processOpinion(opinionTransfer, user);

        Definition definition = new Definition();
        definition.unlike();
        verify(definitionRepo).save(definition);
        verify(opinionRepo).deleteById(0L);
    }

    @Test
    public void shouldLikeAndUndislike_ifAlreadyDisliked() {
        given(definitionRepo.findById(DEFINITION_ID)).willReturn(Optional.of(new Definition()));
        Optional<Opinion> existingOpinion = Optional.of(new Opinion(-1, DEFINITION_ID, user));
        given(opinionRepo.findByDefinitionIdAndUserId(DEFINITION_ID, user.getId())).willReturn(existingOpinion);

        subject.processOpinion(opinionTransfer, user);

        Definition definition = new Definition();
        definition.like();
        definition.undislike();
        verify(definitionRepo).save(definition);
        verify(opinionRepo).save(new Opinion(opinionTransfer.getOpinion(), opinionTransfer.getDefinitionId(), user));
    }
}
