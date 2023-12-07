This is a simple app to generate a macro for roll20. This is inteded to produce an output that gives everything you need to cast a spell from Pathfinder 2e.

The intended usecase is a spellcaster with limited coding skill and access to the online srd. You enter your character's level and casting modifier, along with the name of the spell. Damage currently only supports a single die type, up to 20 dice. Setting the number of dice to 0 prevents the damage row from displaying. Attack rolls and saving throws are automatically calculated based on level, proficiency, and casting modifier. Any special effects based on stage of success can be entered; blank sections will not be included.

After filling in all information, the generate button will write the propper macro into the text area at the bottom. This can be copy/pasted into roll20's built in macro system.
