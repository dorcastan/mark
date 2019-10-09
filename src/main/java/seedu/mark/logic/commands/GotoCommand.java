package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.GotoCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Url;

/**
 * Opens a bookmark.
 */
public class GotoCommand extends Command {

    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the bookmark identified by the index used in the displayed bookmark list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_GOTO_BOOKMARK_ACKNOWLEDGEMENT = "Opening Bookmark: %1$s";

    private final Index targetIndex;

    public GotoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Bookmark> lastShownList = model.getFilteredBookmarkList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
        }

        Bookmark bookmarkToOpen = lastShownList.get(targetIndex.getZeroBased());
        Url urlToOpen = bookmarkToOpen.getUrl();

        return new GotoCommandResult(String.format(MESSAGE_GOTO_BOOKMARK_ACKNOWLEDGEMENT, bookmarkToOpen), urlToOpen);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GotoCommand // instanceof handles nulls
                && targetIndex.equals(((GotoCommand) other).targetIndex)); // state check
    }

}
