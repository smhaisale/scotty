/**
 * This package contains managers for different entities specific to this service.
 *
 * Each manager handles a single entity only, and is independent of communications to external services.
 *
 * For example, a dialogHistoryManager is agnostic to whether the dialog history is retrieved from a service or a
 * database. It simply uses the DAO it has to communicate.
 */

package scotty.manager;