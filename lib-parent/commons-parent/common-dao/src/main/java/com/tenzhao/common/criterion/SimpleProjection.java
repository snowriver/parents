package com.tenzhao.common.criterion;


/**
 * A single-column projection that may be aliased
 *
 * @author Gavin King
 */
public abstract class SimpleProjection implements EnhancedProjection {
	private static final int NUM_REUSABLE_ALIASES = 40;
	private static final String[] REUSABLE_ALIASES = initializeReusableAliases();

	private static String[] initializeReusableAliases() {
		final String[] aliases = new String[NUM_REUSABLE_ALIASES];
		for ( int i = 0; i < NUM_REUSABLE_ALIASES; i++ ) {
			aliases[i] = aliasForLocation( i );
		}
		return aliases;
	}

	private static String aliasForLocation(final int loc) {
		return "y" + loc + "_";
	}

	private static String getAliasForLocation(final int loc) {
		if ( loc >= NUM_REUSABLE_ALIASES ) {
			return aliasForLocation( loc );
		}
		else {
			return REUSABLE_ALIASES[loc];
		}
	}


}
