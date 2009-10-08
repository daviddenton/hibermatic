HIBERMATIC RELEASE NOTES
========================

v1.3.0
======
- added paging mechanics. Current criteria exposing methods such as toCriteria() and toCountCriteria() deprecated. These will be removed in v2.

Still outstanding:
- Possible action to split out PagableSearchFilter interface and Abstract class.
- Introduce extensive tests using in memory db.


v1.2.0
======
- some updates to collection filters. Breaking change to rename a method in NumberFieldFilter.
- fixed SortFieldFilter to default to ascending order if no other ordering applied.

v1.1.1
======
- support for all hibernate join types

v1.1
====
- migration into new source control